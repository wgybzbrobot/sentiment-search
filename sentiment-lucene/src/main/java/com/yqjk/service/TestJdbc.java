package com.yqjk.service;

import java.sql.*;

public class TestJdbc {
	// String dbUrl = "jdbc:oracle:thin:@dvd";
	// String dbUrl = "jdbc:oracle:thin:@//127.0.0.1:1521/dave";
	String dbUrl = "jdbc:oracle:thin:@192.168.1.24:1521:yqjk";
	String theUser = "yqjk";
	String thePw = "yqjk";
	Connection c = null;
	Statement conn;
	ResultSet rs = null;

	public TestJdbc() {
		try {
			// System.setProperty("oracle.net.tns_admin",
			// "D:\\app\\Administrator\\product\\11.2.0\\dbhome_1\\NETWORK\\ADMIN");
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			// Class.forName("oracle.jdbc.OracleDriver").newInstance();
			c = DriverManager.getConnection(dbUrl, theUser, thePw);
			conn = c.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean executeUpdate(String sql) {
		try {
			conn.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ResultSet executeQuery(String sql) {
		rs = null;
		try {
			rs = conn.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void close() {
		try {
			conn.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
