package com.wstore.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.wstore.DAO.OrderDetailDAO;
import com.wstore.entities.Customer;
import com.wstore.entities.Order;
import com.wstore.entities.OrderDetail;


public class SendMail{
	
	
	public void send(Order order, Customer customer) {
		Thread thread =new Thread(){
			@Override
			public void run() {
				final String username = "kienle443@gmail.com";
				final String password = "1234kien";
				OrderDetailDAO detailDAO = new OrderDetailDAO();
				List<OrderDetail> list = new ArrayList<>();
				
				list = detailDAO.findOrderDetailByOrderId(order.getOrderId());
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

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(username));
					message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(customer.getEmail()));
					message.setSubject("Watches Store order");
					message.setText("Dear "+customer.getFullName()+"\n\n Thanks for shopping at Watches Store");
					String content = "<p>Dear "+customer.getFullName()+"</p>";
					content += "<p>Thanks for shopping at Watches Store</p>";
					content += "<p>Here is your checkout order</p>";
					
					content += "<table border=\"1px solid #DDD\" style=\"width:100%; border-collapse:collapse\">"+
									"<thead>"+
									"<tr style=\"background:#DDD; font-size:20px;\">"+
										"<th style=\"text-align: center;padding:10px;\">Product</th>"+
										"<th style=\"text-align: center;padding:10px;\">Product price</th>"+
										"<th style=\"text-align: center;padding:10px;\">Quantity</th>"+
										"<th style=\"text-align: center;padding:10px;\">Discount</th>"+
										"<th style=\"text-align: center;padding:10px;\">Total</th>"+
									"</tr>"+
									"</thead>"+
									"<tbody>";
					for (OrderDetail orderDetail : list) {
						content += "<tr>"+
									"<td style=\"padding:10px;\">"+orderDetail.getProduct().getProductName()+"</td>"+
									"<td style=\"padding:10px;\">$"+orderDetail.getProduct().getProductPrice()+"</td>"+
									"<td style=\"padding:10px;\">"+orderDetail.getQuantity()+"</td>"+
									"<td style=\"padding:10px;\">"+orderDetail.getDiscount()+"%</td>"+
									"<td style=\"padding:10px;\">$"+orderDetail.getPrice()+"</td>"+
									"</tr>";			
					}
										
					content += "</tbody></table>";
					content += "<b style=\"font-size:18px;float:right;color:blue\">Total amount: $"+order.getTotal_amount()+"</b>";
					message.setContent(content,"text/html");
					
					Transport.send(message);
				} catch (MessagingException mex) {
					mex.printStackTrace();
				}
			}
		
		};
		thread.start();
		
	}
	
	public void forgotPassword(String address, String pass){
		Thread thread = new Thread(){
			@Override
			public void run() {
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

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(username));
					message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(address));
					message.setSubject("Reset account password Watches Store");
					message.setText("Your account password is: "+ pass);
					
					Transport.send(message);
				} catch (MessagingException mex) {
					mex.printStackTrace();
				}		
			}
		};
		thread.start();
	}
}