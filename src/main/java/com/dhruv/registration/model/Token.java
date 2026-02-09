package com.dhruv.registration.model;
import java.time.LocalDateTime;
public class Token{
	private String TokenId;
	private String UserId;
	private boolean isVerified;
	private int code;
	private LocalDateTime CreatedAt;
	
	public Token(String TokenId,String UserId,Boolean isVerified,int code) {
		this.TokenId=TokenId;
		this.UserId = UserId;
		this.isVerified = isVerified;
		this.code = code;
		
	}
	public void setTokenId(String TokenId) {
		this.TokenId = TokenId;
	}
	public String getTokenId() {
		return this.TokenId;
	}
	public void setUserId(String UserId) {
		this.UserId = UserId;
	}
	public String getUserId() {
		return this.UserId;
	}
	public void setisVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}
	public boolean getisVerified() {
		return this.isVerified;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getCode() {
		return this.code;
	}
	public void setCreatedAt(LocalDateTime x) {
		this.CreatedAt = x;
	}
	public LocalDateTime getCreatedAt() {
		return this.CreatedAt;
	}
}