     package com.diaspark.INB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.MappingException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.diaspark.INB.DTO.SendMailDTO;

@Service
public class ContactUsMailService {
	private JavaMailSender javaMailSender;
	@Autowired
	public void MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	public void sendEmail(SendMailDTO sendMailDTO) throws MappingException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(sendMailDTO.getToAddress());
		mail.setSubject(sendMailDTO.getSubject());
		mail.setText(sendMailDTO.getQueries());

		/*
		 * This sendQuery() contains an Object of SimpleMailMessage as an Parameter
		 */
		javaMailSender.send(mail);
	}

}
