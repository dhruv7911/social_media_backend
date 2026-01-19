package com.dhruv.registration.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database{
	private static final String URL = "jdbc:mysql://localhost:3306/social_media";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static Connection connection = null;
	
	public static Connection getConnection() {
	    try {
	        if(connection == null || connection.isClosed()) {
	            Class.forName("com.mysql.cj.jdbc.Driver"); // Capital 'D' in Driver
	            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); 
	            System.out.println("connection success");
	        }
	    } catch(SQLException e) {
	        System.err.println("Database Connection failed: " + e.getMessage());
	    } catch(ClassNotFoundException e) {
	        System.err.println("MySQL Driver not found: " + e.getMessage());
	    }
	    return connection;
	}
	public static void closeConnection() {
		try {
			if(connection !=null && !connection.isClosed()) {
				connection.close();
			}
		}catch(SQLException e) {
			System.err.println("Error closing database: "+e.getMessage());
		}
	}

}