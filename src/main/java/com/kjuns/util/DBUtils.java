package com.kjuns.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public DBUtils() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Connection getConntion() {
		if (null == conn) {
			try {
				conn = DriverManager.getConnection(SysConf.JDBC_URL, SysConf.JDBC_USERNAME, SysConf.JDBC_PWD);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return conn;
	}

	public void closeResources() {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public boolean cch(String name, String nationality, String remark) {
		boolean flag = true;
		String sql = "insert into kjuns_visitor (name,nationality,remark) values(?,?,?)";
		conn = this.getConntion();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name); // 对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
			pstmt.setString(2, nationality);
			pstmt.setString(3, remark);
			int i = pstmt.executeUpdate();
			if (i == 0) {
				flag = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return flag;
	}

	public boolean cchname(String name) {
		boolean flag = true;
		String sql = "insert into kjuns_filter_vocabulary (name) values(?)";
		conn = this.getConntion();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name); // 对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
			int i = pstmt.executeUpdate();
			if (i == 0) {
				flag = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return flag;
	}

	public boolean smslogger(String mobile, Integer sender, Integer type, String content, String remark, String reqIp,
			String createDate) {
		boolean flag = true;
		String sql = "INSERT INTO kjuns_sms_log (mobile, sender, type, content, remark, req_ip, create_date) VALUES (?,?,?,?,?,?,?)";
		conn = this.getConntion();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mobile); // 对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
			pstmt.setInt(2, sender);
			pstmt.setInt(3, type);
			pstmt.setString(4, content);
			pstmt.setString(5, remark);
			pstmt.setString(6, reqIp);
			pstmt.setString(7, createDate);
			int i = pstmt.executeUpdate();
			if (i == 0) {
				flag = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return flag;
	}

	public int execOther(PreparedStatement pstmt) {
		try {
			int affectedRows = pstmt.executeUpdate();
			return affectedRows;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public ResultSet execQuery(PreparedStatement pstmt) {
		try {
			rs = pstmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
