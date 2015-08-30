package com.wearit.shike.web.helper;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailManager {
	private JavaMailSenderImpl mailSender;

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String to, String subject, String msg) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");

		message.setContent(msg, "text/html");
		helper.setTo(to);
		helper.setSubject(subject);
		mailSender.send(message);

	}
}
