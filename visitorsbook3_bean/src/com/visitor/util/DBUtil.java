package com.visitor.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private final String driverName = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://127.0.0.1:3306/visitorweb?serverTimezone=UTC";
	private final String user = "visitor";
	private final String password = "1234";

	private static DBUtil instance = new DBUtil();

	private DBUtil() {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static DBUtil getInstance() {
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public void close(AutoCloseable... closeables) {
		for (AutoCloseable c : closeables) {
			if (c != null) {
				try {
					c.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
