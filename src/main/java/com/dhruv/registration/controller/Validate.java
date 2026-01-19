package com.dhruv.registration.controller;

import java.util.regex.Pattern;

public class Validate{
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
	private static final String PASSWORD_REGEX = "^.{8,}$";
	private static final String USERNAME_REGEX = "^.{4,}$";
	private static final Pattern emailPattern  = Pattern.compile(EMAIL_REGEX);
	private static final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
	private static final Pattern usernamePattern = Pattern.compile(USERNAME_REGEX);
	// CHECK NULL OR EMPTY
	public static boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}
	public static boolean isValidEmail(String email) {
		return email!=null && emailPattern.matcher(email).matches();
	}
	public static boolean isValidUsername(String username) {
		return username!=null && usernamePattern.matcher(username).matches();
	}
	public static boolean isValidPassword(String password1) {
		return password1!=null && passwordPattern.matcher(password1).matches();
	}
	public static boolean passwordMatches(String password1,String password2) {
		return password1!=null&&password1.equals(password2);
	}
}