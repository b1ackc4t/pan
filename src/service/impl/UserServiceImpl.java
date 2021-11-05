package service.impl;

import java.util.Date;
import java.util.List;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import exception.IdIsNullException;
import model.Users;
import service.UserService;

public class UserServiceImpl implements UserService {

	UserDao userDao = new UserDaoImpl();
	
	@Override
	public int addUser(Users users) {
		String username = users.getName();
		String pwd = users.getPwd();
		
		
		return userDao.addUser(users);
	}

	@Override
	public int delUserById(String id) throws IdIsNullException {
		int count = userDao.delUserById(id);
		if (count == 1) {
			return 1;
		}
		return 0;
	}

	@Override
	public int updateUserById(Users users) throws IdIsNullException {
		// TODO Auto-generated method stub
		return userDao.updateUserById(users);
	}

	@Override
	public boolean checkUser(Users users) {
		
		return userDao.checkUser(users);
		
	}
	
	@Override
	public List<List> getShareFile(String name) {
		return userDao.getShareFile(name);
	}
	
	@Override
	public int deleteFile(String path, String filename) {
		return userDao.deleteFile(path, filename);
	}
	
	@Override
	public int insertFile(String name, String path, String filename, long size, Date uploadt, String ext) {
		return userDao.insertFile(name, path, filename, size, uploadt, ext);
	}
	
	@Override
	public String getOwner(String path, String filename) {
		return userDao.getOwner(path, filename);
	}
	
	@Override
	public List<String> getAllName() {
		return userDao.getAllName();
	}
	
	@Override 
	public int insertHome(String name, String path, String filename, long size, Date uploadt, String ext) {
		return userDao.insertHome(name, path, filename, size, uploadt, ext);
	}
	
	@Override
	public int deleteHome(String name, String path, String filename) {
		return userDao.deleteHome(name, path, filename);
	}
	
	@Override
	public int updateHome(String name, String path, String filename, String newfilename) {
		return userDao.updateHome(name, path, filename, newfilename);
	}
	
	@Override
	public List<List> FindHome(String name, String key) {
		return userDao.FindHome(name, key);
	}
	
	@Override
	public List<List> FindHomeShare(String name, String key) {
		return userDao.FindHomeShare(name, key);
	}
	
	@Override
	public List<List> FindPublicShare(String key) {
		return userDao.FindPublicShare(key);
	}
	
	@Override
	public boolean checkAdminUser(Users users) {
		
		return userDao.checkAdminUser(users);
		
	}
	
	@Override
	public List<List> selectAll() {
		return userDao.selectAll();
	}
	
	@Override
	public int delUserByName(String name) throws IdIsNullException {
		int count = userDao.delUserByName(name);
		if (count == 1) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public List<String> selectRenamePath(String path, String dirname) {
		return userDao.selectRenamePath(path, dirname);
	}

	@Override
	public int delShareDir(String path, String dirname) throws IdIsNullException {
		return userDao.delShareDir(path, dirname);
	}
	
	@Override
	public int updatePwd(String name, String pwd) throws IdIsNullException {
		return userDao.updatePwd(name, pwd);
	}
	
	@Override
	public List<List> findUser(String key) {
		return userDao.findUser(key);
	}
}