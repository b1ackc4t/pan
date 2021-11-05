package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Util {
	
	private static ComboPooledDataSource cpds = new ComboPooledDataSource("mysql");
	

	
	public static Connection getConnection() throws SQLException{
		return cpds.getConnection();
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
	
	public static void main(String[] args) throws SQLException {
		
	}

}
