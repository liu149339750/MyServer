package com.lw.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class EmailSend {

	
	public static void sendEmail(String title,String message){
		String from = "liu149339750@163.com";
	    Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.163.com");
	    props.put("mail.smtp.from", from);
	    Session session = Session.getInstance(props);
	    try {
	        MimeMessage msg = new MimeMessage(session);
	        msg.setRecipients(Message.RecipientType.TO,
	                          "hnyjliuwei@163.com");
	        msg.setFrom(new InternetAddress(from, "server message"));
	        msg.setSubject(title);
	        msg.setSentDate(new Date());
	        msg.setText(message);
	        SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
	        transport.connect("smtp.163.com", from, "382700045");
	        msg.saveChanges();
	        transport.sendMessage(msg, msg.getAllRecipients());
	    } catch (MessagingException mex) {
	        System.out.println("send failed, exception: " + mex);
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
