package com.smart.manager.service;

import java.util.Properties;
import org.springframework.stereotype.Service;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderServiceClass {

	
	
	public boolean sendEmail(String to,String from,String subject,String text) {
		
		boolean flag=false;
		
		Properties properties=new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port",587);
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.auth","true");
		
		String username="hannanshaikh150";
		String password="eviqxqdfpzzzvxgj";
		
		//Setting up the session object
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		try{
			//Composing the message
			Message msg= new MimeMessage(session);
			msg.setRecipient(Message.RecipientType.TO,new InternetAddress(to) );
			msg.setFrom(new InternetAddress(from));
			msg.setSubject(subject);
			msg.setText(text);
			
			//Sending mail
			Transport.send(msg);
			flag=true;
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return flag;
	}
	
	
}
