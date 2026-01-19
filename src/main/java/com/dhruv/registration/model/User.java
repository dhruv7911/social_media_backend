package com.dhruv.registration.model;
import java.time.LocalDateTime;
public class User{
	private String UserId;
	private String Username;
	private String Email;
	private LocalDateTime LastLoginAt;
	private LocalDateTime CreatedAt;
	private String Password;
	private String ConfirmPassword;
	private String ProfilePictureUrl;
	//constructor;
	public User(String UserId,String Username,String Email,LocalDateTime LastLoginAt,String Password,String ConfirmPassword,String ProfilePictureUrl) {
		this.UserId = UserId;
		this.Username = Username;
		this.Email = Email;
		this.LastLoginAt = LastLoginAt;
		this.Password = Password;
		this.ConfirmPassword=ConfirmPassword;
		this.ProfilePictureUrl = ProfilePictureUrl;
	}
	//getter and setter;
	public void setUserId(String x) {
		this.UserId = x;
	}
	public String getUserId() {
		return this.UserId;
	}
	
	public void setEmail(String x) {
		this.Email = x;
	}
	public String getEmail() {
		return this.Email;
	}
	public void setUsername(String x) {
		this.Username = x;
	}
	public String getUsername() {
		return this.Username;
	}
	public void setCreatedAt(LocalDateTime x) {
		this.CreatedAt= x;
	}
	public LocalDateTime getCreatedAt() {
		return this.CreatedAt;
	}
	public void setLastLoginAt(LocalDateTime x) {
		this.LastLoginAt = x;
	}
	public LocalDateTime getLastLoginAt() {
		return this.LastLoginAt;
	}
	public void setPassword(String x) {
		this.Password = x;
	}
	public void setConfirmPassword(String x) {
		this.ConfirmPassword = x;
	}
	public String getPassword() {
		return this.Password;
	}
	public String getConfirmPassword() {
		return this.ConfirmPassword;
	}
	public void setProfilePictureUrl(String x) {
		this.ProfilePictureUrl = x;
	}
	public String getProfilePictureUrl() {
		return this.ProfilePictureUrl;
	}
}