package com.dhruv.registration.dao;
import com.dhruv.registration.model.User;
import com.dhruv.registration.util.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao{
	private static final String CHECK_EMAIL_EXISTS = "SELECT COUNT(*) FROM Users WHERE Email = ?";
	private static final String CHECK_USERNAME_EXISTS = "SELECT COUNT(*) FROM Users WHERE Username= ?";
	private static final String INSERT_NEW_USER = "INSERT INTO Users("
			+ "UserId,"
			+ "Email,"
			+ "Password,"
			+ "Username,"
			+ "CreatedAt,"
			+ "LastLoginAt,"
			+ "ProfilePictureUrl"
			+ ")VALUES(?,?,?,?,NOW(),NOW(),?)";
	public static boolean isEmailExists(String email) {
		boolean result = false;
		try(Connection conn = Database.getConnection();
			PreparedStatement ps = conn.prepareStatement(CHECK_EMAIL_EXISTS);
				){
			ps.setString(1, email);
			try(ResultSet rs  = ps.executeQuery()){
				if(rs.next()) {
					result = rs.getInt(1) > 0;
				}
			}
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}
	public static boolean isUsernameExists(String username) {
		boolean result = false;
		try(Connection conn = Database.getConnection();
			PreparedStatement ps = conn.prepareStatement(CHECK_USERNAME_EXISTS);
				){
			ps.setString(1, username);
			try(ResultSet rs  = ps.executeQuery()){
				if(rs.next()) {
					result = rs.getInt(1) > 0;
				}
			}
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}
	public static boolean insertUser(User user) {
		boolean result = false;
		try(Connection conn = Database.getConnection();
			PreparedStatement ps = conn.prepareStatement(INSERT_NEW_USER);
				){
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getUsername());
			ps.setString(5,null);
			int rowAffected = ps.executeUpdate();
			result = rowAffected > 0;
		}catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}
	
}