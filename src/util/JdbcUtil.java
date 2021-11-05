package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import java.sql.Statement;
/**
 * <:&lt;
 * >:&gt;
 * &nbsp;
 * &:&amp;
 * @author Administrator
 *
 */
public class JdbcUtil {
	
	private static String driverClassName=null;
	private static String url=null;
	private static String user=null;
	private static String pwd=null;
	
	static{
		InputStream is=JdbcUtil.class.getClassLoader().getResourceAsStream("dbconfig.properties");
		Properties props=new Properties();
		try {
			props.load(is);
			
			driverClassName=props.getProperty("mysql.driverClassName");
			url=props.getProperty("mysql.url");
			user=props.getProperty("mysql.user");
			pwd=props.getProperty("mysql.pwd");
			
			Class.forName(driverClassName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, user, pwd);
	}
	
	public static void release(ResultSet rs,Statement ps,Connection conn) throws SQLException{
		if (rs != null) {
			rs.close();
			rs = null; // 垃圾回收，上！
		}
		if (ps != null) {
			ps.close();
			ps = null; // 垃圾回收，上！
		}
		if (conn != null) {
			conn.close();
			conn = null; // 垃圾回收，上！
		}
	}
	
	public static void main(String[] args) {
		
		
		
		
	}

}
