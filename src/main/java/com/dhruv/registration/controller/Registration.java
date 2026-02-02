package com.dhruv.registration.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

import com.dhruv.registration.dao.UserDao;
//defined classes
import com.dhruv.registration.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson;

	@Override
	public void init() throws ServletException {
		// Create Gson with LocalDateTime support
		gson = new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, _, _) -> {
					String dateTime = json.getAsString();
					if (dateTime == null || dateTime.isEmpty()) {
						return null;
					}
					return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
				}).registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, _, context) -> {
					if (src == null) {
						return null;
					}
					return context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				}).create();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// Read JSON from request body
		StringBuilder jsonBuilder = new StringBuilder();
		String line;
		BufferedReader reader = req.getReader();

		while ((line = reader.readLine()) != null) {
			jsonBuilder.append(line);
		}

		String jsonString = jsonBuilder.toString();

		// Parse JSON to User object (now it works!)
		User user = gson.fromJson(jsonString, User.class);

		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		JsonObject json = new JsonObject();
		if (Validate.isEmpty(user.getPassword())) {
			json.addProperty("status", "error");
			json.addProperty("message", "password required");
			PrintWriter out = res.getWriter();
			out.println(json);
			return;
		}
		if (Validate.isEmpty(user.getConfirmPassword())) {
			json.addProperty("status", "error");
			json.addProperty("message", "confirm password required");
			PrintWriter out = res.getWriter();
			out.println(json);
			return;
		}
		if (Validate.isEmpty(user.getUsername())) {
			json.addProperty("status", "error");
			json.addProperty("message", "username required");
			PrintWriter out = res.getWriter();
			out.println(json);
			return;
		}
		if (Validate.isEmpty(user.getEmail())) {
			json.addProperty("status", "error");
			json.addProperty("message", "email required");
			PrintWriter out = res.getWriter();
			out.println(json);
			return;
		}
		if (!Validate.isValidEmail(user.getEmail())) {
			json.addProperty("status", "error");
			json.addProperty("message", "invalid email");
			PrintWriter out = res.getWriter();
			out.println(json);
			return;
		}
		if (!Validate.isValidUsername(user.getUsername())) {
			json.addProperty("status", "error");
			json.addProperty("message", "username must be minimum 4 characters");
			PrintWriter out = res.getWriter();
			out.println(json);
			return;
		}
		if (!Validate.isValidPassword(user.getPassword())) {
			json.addProperty("status", "error");
			json.addProperty("message", "password must be minimum 8 characters");
			PrintWriter out = res.getWriter();
			out.println(json);
			return;
		}
		if (!Validate.passwordMatches(user.getPassword(), user.getConfirmPassword())) {

			json.addProperty("status", "error");
			json.addProperty("message", "password must matches");
			PrintWriter out = res.getWriter();
			out.println(json);
			return;
		}
		if (UserDao.isEmailExists(user.getEmail())) {
			json.addProperty("status", "error");
			json.addProperty("message", "email address already exists");
			PrintWriter out = res.getWriter();
			out.println(json);
			return;
		}

		if (UserDao.isUsernameExists(user.getUsername())) {
			json.addProperty("status", "error");
			json.addProperty("message", "username already taken");
			PrintWriter out = res.getWriter();
			out.println(json);
			return;
		}
		// everything is fine then insert user details in database (Users Table)

		String UserId = UUID.randomUUID().toString();
		user.setUserId(UserId);
		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
		user.setPassword(hashedPassword);
		
		if(UserDao.insertUser(user)) {
			json.addProperty("status", "success");
			json.addProperty("message", "registered Successfully");
			PrintWriter out = res.getWriter();
			out.println(json);
			return;
		}
	}
}