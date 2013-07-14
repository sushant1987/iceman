/**
 * 
 */
package com.amzedia.xstore.web.rest;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amzedia.xstore.model.Mail;

/**
 * @author Sushant
 * 
 */
@Controller
@RequestMapping(value = "/mail")
public class MailRestService {

	@RequestMapping(value = "/sendmail", method = RequestMethod.POST)
	@ResponseBody public String sendMail(@RequestBody final Mail mail) {
		String response;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", mail.getHost());
		props.put("mail.smtp.port", mail.getPort());
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								mail.getSender(),
								mail.getPassword());
					}
				});
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mail.getSender()));
			message.setRecipients(
					Message.RecipientType.TO,
					InternetAddress.parse(mail.getReciver()));
			message.setSubject(mail.getSubject());
			message.setText(mail.getMessage());
			Transport.send(message);
			response = "success";

		} catch (MessagingException e) {
			response = "fail";
			throw new RuntimeException(e);
		}
		return response;
	}
	
	@RequestMapping(value = "/dummy", method = RequestMethod.GET)
	@ResponseBody public Mail getDummy() {
		Mail mail = new Mail();
		mail.setHost("smtp.gmail.com");
		mail.setPort("587");
		mail.setSender("sushant1887@gmail.com");
		mail.setReciver("sushant1887@gmail.com");
		mail.setSubject("Hi Chutiya");
		mail.setMessage("tum chutiye ho");
		mail.setPassword("Gandalf123#");
		return mail;
	}

}
