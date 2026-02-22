package com.dhruv.registration.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database{
	private static final String URL = System.getenv("DB_URL");
	private static final String USERNAME = System.getenv("DB_USER");
	private static final String PASSWORD = System.getenv("DB_PASSWORD");
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		
	    try {
	        if(connection == null || connection.isClosed()) {
	        	System.out.println("URL=" + URL);
	        	System.out.println("USER=" + USERNAME);
	        	System.out.println("PASS=" + PASSWORD);
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