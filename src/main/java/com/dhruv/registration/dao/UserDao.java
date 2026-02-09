package com.dhruv.registration.dao;
import com.dhruv.registration.model.User;
import com.dhruv.registration.util.Database;
import com.dhruv.registration.model.Token;
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
			+ "ProfilePictureUrl,"
			+ "TokenId"
			+ ")VALUES(?,?,?,?,NOW(),NOW(),?,?)";
	private static final String INSERT_TOKEN = "INSERT INTO Tokens("
			+"TokenId,"
			+"UserId,"
			+"isVerified,"
			+"code,"
			+"CreatedAt"
			+")VALUES(?,?,?,?,NOW())";
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
   public static boolean insertToken(Token token) {
		boolean result = false;
		try(Connection conn = Database.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_TOKEN);
					){
			ps.setString(1,token.getTokenId());
			ps.setString(2, token.getUserId());
			ps.setBoolean(3, token.getisVerified());
			ps.setInt(4,token.getCode());
			int rowAffected = ps.executeUpdate();
			if(rowAffected>0) {
				result =true;
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
			ps.setString(6, user.getTokenId());
			int rowAffected = ps.executeUpdate();
			if(rowAffected > 0) {
				Token token = new Token(user.getTokenId(),user.getUserId(),false,((int)(Math.random()*900000)));
				result = insertToken(token);
				
			}
		}catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return result;
		
	}

}