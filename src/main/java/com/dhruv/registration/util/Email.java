package com.dhruv.registration.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class Email{
	public void setMail(){
		Properties props = new Properties();
		props.put("mail.smtp.host","smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(props,new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(
					"your@email.com","yourpassword"
						);
			}
		});
		session.setDebug(true);
	}
	public void sendMail() {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("sender@gmail.com"));
		message.setRecipients(
				Message.RecipientType.TO,
				InternetAddress.parse("recipient@email.com")
				);
		MimeMultipart multipart = new MimeMultipart("alternative");
		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent("<h1>Hello!</h1>","text/html;");
		multipart.addBodyPart(htmlPart);
		message.setContent(multipart)
		Transport.send(message);
		System.out.println("✓ Email sent!");
	}
}
