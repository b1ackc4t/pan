package dao.impl;


import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.C3P0Util;
import util.DBHelper;
import dao.UserDao;
import exception.IdIsNullException;
import model.Users;

public class UserDaoImpl implements UserDao {

	
	@Override
	public int addUser(Users users) {
		String name = users.getName();
		String pwd = users.getPwd();
		
		try {

			Connection conn = null;

			String sql = "insert into users (name,pwd) values (?,?)";
			String[] params = { name, pwd };

			int count = DBHelper.executeUpdate(sql, params);

			return count;
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int delUserById(String id) throws IdIsNullException {
	
		try {

			Connection conn = null;

			String sql = "delete from users where id=?";
			String[] params = {id};

			int count = DBHelper.executeUpdate(sql, params);

			return count;
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int updateUserById(Users users) throws IdIsNullException {
		
		String id = String.valueOf(users.getId());
		String name = users.getName();
		String pwd = users.getPwd();
		try {

			Connection conn = null;

			String sql = "update users set name=?,pwd=? where id=?";
			String[] params = {name, pwd, id};

			int count = DBHelper.executeUpdate(sql, params);

			return count;
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public boolean checkUser(Users users) {
		
		boolean flag = true;

		String name = users.getName();
		String pwd = users.getPwd();

		try {

			Connection conn = null;

			String sql = "select * from users where name=? and pwd=? ";
			String[] params = { name, pwd };

			ResultSet rs = DBHelper.executeQuery(sql, params);

			if (rs.next() == false) {
				flag = false;
			}
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	@Override
	public List<List> getShareFile(String name) {
		List<List> li = null;
		try {

			Connection conn = null;

			String sql = "select path, filename from publicshare where name=? ";
			String[] params = { name };
			
			ResultSet rs = DBHelper.executeQuery(sql, params);

			if (rs.next()) {
				li = new ArrayList<List>();
			}

			do {
				List<String> info = new ArrayList<String>();
				info.add(rs.getString("path"));
				info.add(rs.getString("filename"));
				
				li.add(info);
				

			} while (rs.next());
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return li;
	}
	
	@Override
	public int deleteFile(String path, String filename) {
	
		try {

		

			String sql = "delete from publicshare where path=? and filename=? ";
			String[] params = {path, filename};

			int count = DBHelper.executeUpdate(sql, params);

			return count;
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public int insertFile(String name, String path, String filename, long size, Date uploadt, String ext) {
		try {

			

			String sql = "insert into publicshare values(?,?,?,?,?,?)";
			Object[] params = {name, path, filename, size, uploadt, ext};

			int count = DBHelper.executeUpdate(sql, params);

			return count;
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public String getOwner(String path, String filename) {


		try {


			String sql = "select name from publicshare where path=? and filename=? ";
			String[] params = { path, filename };

			ResultSet rs = DBHelper.executeQuery(sql, params);

			if (rs.next() != false) {
				return rs.getString("name");
			}
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
	
	@Override
	public List<String> getAllName() {
		List<String> li = null;
		try {

			Connection conn = null;

			String sql = "select name from users ";

			
			ResultSet rs = DBHelper.executeQuery(sql, null);

			if (rs.next()) {
				li = new ArrayList<String>();
			}

			do {
				li.add(rs.getString("name"));
				

			} while (rs.next());
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return li;
	}
	
	@Override
	public int insertHome(String name, String path, String filename, long size, Date uploadt, String ext) {
		try {

			

			String sql = "insert into userfile values(?,?,?,?,?,?)";
			Object[] params = {name, path, filename, size, uploadt, ext};

			int count = DBHelper.executeUpdate(sql, params);

			return count;
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public int deleteHome(String name, String path, String filename) {
		try {

			

			String sql = "delete from userfile where name=? and path=? and filename=?";
			Object[] params = {name, path, filename};

			int count = DBHelper.executeUpdate(sql, params);

			return count;
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public int updateHome(String name, String path, String filename, String newfilename) {

		try {

			Connection conn = null;

			String sql = "update userfile set filename=? where name=? and path=? and filename=?";
			
			String[] params = {newfilename, name, path, filename};

			int count = DBHelper.executeUpdate(sql, params);

			return count;
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public List<List> FindHome(String name, String key) {
		List<List> li = null;
		try {


			String sql = "select filename, path, size, uploadt, ext from userfile where name=? and (filename like concat('%',?,'%') or uploadt like concat('%',?,'%') or ext like concat('%',?,'%')) ";
			String[] params = { name, key, key, key };
			
			ResultSet rs = DBHelper.executeQuery(sql, params);

			if (rs.next()) {
				li = new ArrayList<List>();
			}

			do {
				List<String> info = new ArrayList<String>();
				info.add(rs.getString("filename"));
				info.add(rs.getString("path"));
				info.add(rs.getString("size"));
				info.add(rs.getString("uploadt"));
				info.add(rs.getString("ext"));
				li.add(info);
				

			} while (rs.next());
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return li;
	}
	
	@Override
	public List<List> FindHomeShare(String name, String key) {
		List<List> li = null;
		try {


			String sql = "select filename, path, size, uploadt, ext from publicshare where name=? and (filename like concat('%',?,'%') or uploadt like concat('%',?,'%') or ext like concat('%',?,'%')) ";
			String[] params = { name, key, key, key };
			
			ResultSet rs = DBHelper.executeQuery(sql, params);

			if (rs.next()) {
				li = new ArrayList<List>();
			}

			do {
				List<String> info = new ArrayList<String>();
				info.add(rs.getString("filename"));
				info.add(rs.getString("path"));
				info.add(rs.getString("size"));
				info.add(rs.getString("uploadt"));
				info.add(rs.getString("ext"));
				li.add(info);
				

			} while (rs.next());
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return li;
	}
	
	@Override
	public List<List> FindPublicShare(String key) {
		List<List> li = null;
		try {


			String sql = "select name, filename, path, size, uploadt, ext from publicshare where (name like concat('%',?,'%') or filename like concat('%',?,'%') or uploadt like concat('%',?,'%') or ext like concat('%',?,'%')) ";
			String[] params = { key, key, key, key };
			
			ResultSet rs = DBHelper.executeQuery(sql, params);

			if (rs.next()) {
				li = new ArrayList<List>();
			}

			do {
				List<String> info = new ArrayList<String>();
				info.add(rs.getString("filename"));
				info.add(rs.getString("name"));
				info.add(rs.getString("path"));
				info.add(rs.getString("size"));
				info.add(rs.getString("uploadt"));
				info.add(rs.getString("ext"));
				li.add(info);
				

			} while (rs.next());
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return li;
	}
	
	@Override
	public boolean checkAdminUser(Users users) {
		
		boolean flag = true;

		String name = users.getName();
		String pwd = users.getPwd();

		try {

			Connection conn = null;

			String sql = "select * from admin where name=? and pwd=? ";
			String[] params = { name, pwd };

			ResultSet rs = DBHelper.executeQuery(sql, params);

			if (rs.next() == false) {
				flag = false;
			}
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	@Override
	public List<List> selectAll() {
		List<List> userli = null;
		
		try {


			String sql = "select * from users";

			ResultSet rs = DBHelper.executeQuery(sql, null);

			if (rs.next()) {
				userli = new ArrayList<List>();
			}

			do {
				List<String> info = new ArrayList<String>();
				info.add(rs.getString("name"));
				info.add(rs.getString("pwd"));
				userli.add(info);
				

			} while (rs.next());
			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userli;
		
		
	}
	
	public int delUserByName(String name) throws IdIsNullException {
		
		try {

			Connection conn = null;

			String sql = "delete from users where name=?";
			String sql2 = "delete from publicshare where name=?";
			String sql3 = "delete from userfile where name=?";
			String[] params = {name};

			int count = DBHelper.executeUpdate(sql, params);
			DBHelper.executeUpdate(sql2, params);
			DBHelper.executeUpdate(sql3, params);
			return count;
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int updateShareDir(String path, String dirname) throws IdIsNullException {
		
//		String sep = System.getProperty("file.separator");
//		try {
//
//			
//			String oldpath = "^" + path + sep + dirname + ".*";
//			String newpath = path
//			String sql = "update publicshare set path=path where path regex ?";
//			String[] params = {name, pwd, id};
//
//			int count = DBHelper.executeUpdate(sql, params);
//
//			return count;
//			
////			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return 0;
		return 0;
	}
	
	public List<String> selectRenamePath(String path, String dirname) {
		String sep = System.getProperty("file.separator");
		List<String> li = null;
		try {
			String oldpath = "^" + path + sep + dirname + ".*";

			String sql = "select path from publicshare where path regexp ? ";
			String[] params = { oldpath };
			
			ResultSet rs = DBHelper.executeQuery(sql, params);

			if (rs.next()) {
				li = new ArrayList<String>();
			}

			do {
				li.add(rs.getString("path"));
				

			} while (rs.next());
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return li;
	}
	
	@Override
	public int delShareDir(String path, String dirname) throws IdIsNullException {
		try {

			Connection conn = null;
			String rep = path + dirname;
			String sql = "delete from publicshare where (path like concat(?,'%'))";
			String[] params = {rep};
			
			int count = DBHelper.executeUpdate(sql, params);

			return count;
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public int updatePwd(String name, String pwd) throws IdIsNullException {
		try {


			String sql = "update users set pwd=? where name=?";
			String[] params = {pwd, name};

			int count = DBHelper.executeUpdate(sql, params);

			return count;
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public List<List> findUser(String key) {
		List<List> li = null;
		try {


			String sql = "select * from users where (name like concat('%',?,'%')) ";
			String[] params = { key };
			
			ResultSet rs = DBHelper.executeQuery(sql, params);

			if (rs.next()) {
				li = new ArrayList<List>();
			}

			do {
				List<String> info = new ArrayList<String>();
				info.add(rs.getString("name"));
				info.add(rs.getString("pwd"));

				li.add(info);
				

			} while (rs.next());
			
//			C3P0Util.release(rs, DBHelper.getPs(), DBHelper.getConn());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return li;
	}
	
}
	

