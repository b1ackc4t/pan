package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {

	private static Connection conn = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;

	public static Connection getConn() {
		return conn;
	}

	public static PreparedStatement getPs() {
		return ps;
	}

	public static int executeUpdate(String sql, Object[] params)
			throws SQLException {

		int res = -1;

		// conn=JdbcUtil.getConnection();
		conn = C3P0Util.getConnection();
		ps = conn.prepareStatement(sql);

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
		}

		res = ps.executeUpdate();

		// JdbcUtil.release(null, ps, conn);
		C3P0Util.release(null, ps, conn);

		return res;

	}

	public static ResultSet executeQuery(String sql, Object[] params)
			throws SQLException {

		ResultSet rs = null;

		// conn=JdbcUtil.getConnection();
		conn = C3P0Util.getConnection();
		ps = conn.prepareStatement(sql);

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
		}

		rs = ps.executeQuery();

		// JdbcUtil.release(null, ps, conn); //延迟关闭
		// C3P0Util.release(null, ps, conn); //延迟关闭

		return rs;

	}

}
