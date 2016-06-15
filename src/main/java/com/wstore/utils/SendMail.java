package com.wstore.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	
	
	public void send(String toAddress) {
		final String username = "kienle443@gmail.com";
		final String password = "1234kien";
		//String to = "abcd@gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			/*MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress());
			message.setSubject("This is the Subject Line!");

			message.setContent("<h1>This is actual message</h1>", "text/html");*/
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toAddress));
			message.setSubject("Testing Subject");
			message.setContent("<table border=\"1px\">"+
								"<thead style=\"background:blue; font-size:18px; padding:4px;\">"+
								"<tr>"+
									"<th>Product</th>"+
									"<th>Product price</th>"+
									"<th>Quantity</th>"+
									"<th>Discount</th>"+
									"<th>Total</th>"+
									"</tr>"+
									"</thead>","text/html");
			message.setContent("<h1>This is actual message</h1>","text/html");
			message.setContent("<h1>This is actual message</h1>","text/html");
			message.setContent("<h1>This is actual message</h1>","text/html");

			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
	
	public void forgotPassword(String address, String pass){
		final String username = "kienle443@gmail.com";
		final String password = "1234kien";
		//String to = "abcd@gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			/*MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress());
			message.setSubject("This is the Subject Line!");

			message.setContent("<h1>This is actual message</h1>", "text/html");*/
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(address));
			message.setSubject("Reset password Watches Store");
			message.setText("Your account password is: "+ pass);
			//message.setContent("<h1>This is actual message</h1>","text/html");
			//message.setText("Dear Mail Crawler,"
			//		+ "\n\n No spam to my email, please!");
			
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}