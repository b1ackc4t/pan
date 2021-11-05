package service;

import java.util.Date;
import java.util.List;

import exception.IdIsNullException;
import model.Users;

public interface UserService {
	
	/**
	 * 添加单个用户
	 * @param users
	 * @return 影响的记录数
	 */
	public int addUser(Users users);

	/**
	 * 根据id删除单个用户
	 * @param id
	 * @return 影响的记录数
	 * @throws IdIsNullException
	 */
	public int delUserById(String id) throws IdIsNullException;
	
	/**
	 * 修改单个用户
	 * @param users
	 * @return 影响的记录数
	 * @throws IdIsNullException
	 */
	public int updateUserById(Users users) throws IdIsNullException;
	
	/**
	 * 检查用户是否存在且密码是否匹配
	 * @param users
	 * @return
	 */
	public boolean checkUser(Users users);
	
	/**
	 * 获取某用户的全部分享文件名列表
	 * @param name
	 * @return List<String>
	 */
	public List<List> getShareFile(String name);
	
	/**
	 * 根据路径和文件名删除文件信息
	 * @param path, filename
	 * @return 影响的记录数
	 */
	public int deleteFile(String path, String filename);
	
	/**
	 * 插入一条共享文件信息
	 * @param name, path, filename
	 * @return 影响的记录数
	 */
	public int insertFile(String name, String path, String filename, long size, Date uploadt, String ext);
	
	/**
	 * 根据path和filename查询所有者
	 * @param name, path, filename
	 * @return name
	 */
	public String getOwner(String path, String filename);
	
	/**
	 * 获取所有用户名字列表
	 * @param 
	 * @return List<String>
	 */
	public List<String> getAllName();
	
	/**
	 * 插入一条用户文件信息
	 * @param name, path, filename， size, uploadt, ext
	 * @return 影响的记录数
	 */
	public int insertHome(String name, String path, String filename, long size, Date uploadt, String ext);
	
	/**
	 * 删除一条用户文件信息
	 * @param name, path, filename
	 * @return 影响的记录数
	 */
	public int deleteHome(String name, String path, String filename);
	
	/**
	 * 更改一条用户文件信息
	 * @param name, path, filename, newfilename
	 * @return 影响的记录数
	 */
	public int updateHome(String name, String path, String filename, String newfilename);
	
	/**
	 * 搜索含有key的用户文件信息
	 * @param name, key
	 * @return 影响的记录数
	 */
	public List<List> FindHome(String name, String key);
	
	/**
	 * 搜索含有key的用户共享文件信息
	 * @param name, key
	 * @return 影响的记录数
	 */
	public List<List> FindHomeShare(String name, String key);
	
	/**
	 * 搜索含有key的公共共享文件信息
	 * @param key
	 * @return 影响的记录数
	 */
	public List<List> FindPublicShare(String key);
	
	/**
	 * 检查管理员是否存在且密码是否匹配
	 * @param users
	 * @return
	 */
	public boolean checkAdminUser(Users users);
	
	/**
	 *查询数据库所有用户数据
	 * @param 
	 * @return 用户信息二维数组
	 */
	public List<List> selectAll();
	
	/**
	 * 根据name删除单个用户
	 * @param name
	 * @return 影响的记录数
	 * @throws IdIsNullException
	 */
	public int delUserByName(String name) throws IdIsNullException;
	
	/**
	 *查询数据库需要重命名的记录
	 * @param 
	 * @return 记录的path构成的数组
	 */
	public List<String> selectRenamePath(String path, String dirname);
	
	/**
	 * 删除共享目录下所有文件
	 * @param path, dirname
	 * @return 影响的记录数
	 * @throws IdIsNullException
	 */
	public int delShareDir(String path, String dirname) throws IdIsNullException;
	
	/**
	 * 修改单个用户密码
	 * @param name, pwd
	 * @return 影响的记录数
	 * @throws IdIsNullException
	 */
	public int updatePwd(String name, String pwd) throws IdIsNullException;
	
	/**
	 *模糊查询用户
	 * @param key
	 * @return 用户信息二维数组
	 */
	public List<List> findUser(String key);
}

