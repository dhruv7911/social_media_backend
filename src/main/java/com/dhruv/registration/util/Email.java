package com.dhruv.registration.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class Email {

    private static Session createSession() {

        String EMAIL = System.getenv("EMAIL");
        String EMAIL_PASSWORD = System.getenv("EMAIL_PASSWORD");

        System.out.println("EMAIL: " + EMAIL);
        System.out.println("PASSWORD: " + EMAIL_PASSWORD);

        Properties props = new Properties();

        // ✅ Gmail SMTP settings
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Optional but good
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, EMAIL_PASSWORD);
            }
        });
    }

    public static void sendOTPEmail(String toEmail, int code) throws MessagingException {

        Session session = createSession();
        session.setDebug(true);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(System.getenv("EMAIL")));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail)
        );

        message.setSubject("Your OTP Verification Code");

        // HTML email body
        MimeMultipart multipart = new MimeMultipart("alternative");
        MimeBodyPart htmlPart = new MimeBodyPart();

        htmlPart.setContent(
                "<h2>Your OTP Code</h2>" +
                "<p>Your verification code is: <b>" + code + "</b></p>" +
                "<p>This OTP is valid for 10 minutes.</p>",
                "text/html; charset=utf-8"
        );

        multipart.addBodyPart(htmlPart);
        message.setContent(multipart);

        Transport.send(message);

        System.out.println("✅ OTP Email sent to: " + toEmail);
    }
}