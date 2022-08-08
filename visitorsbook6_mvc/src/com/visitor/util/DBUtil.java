package com.visitor.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {

//	private final String driverName = "com.mysql.cj.jdbc.Driver";
//	private final String url = "jdbc:mysql://127.0.0.1:3306/visitorweb?serverTimezone=UTC";
//	private final String user = "visitor";
//	private final String password = "1234";

	private static DBUtil instance = new DBUtil();

//	private DBUtil() {
//		try {
//			Class.forName(driverName);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

	public static DBUtil getInstance() {
		return instance;
	}

//	public Connection getConnection() throws SQLException {
//		return DriverManager.getConnection(url, user, password);
//	}
	
	public Connection getConnection() throws SQLException {
		try {
			Context context = new InitialContext();
			Context root = (Context) context.lookup("java:comp/env");
			DataSource ds = (DataSource) root.lookup("jdbc/visitor");
			return ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
		
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
