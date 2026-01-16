package com.example.ecoembes;

import java.security.GeneralSecurityException;
import java.util.Properties;

import org.eclipse.angus.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class TeamWorkSwE11Application {

	public static void main(String[] args) {
		SpringApplication.run(TeamWorkSwE11Application.class, args);
	}
	
	@Bean
	JavaMailSender getJavaMailSender(
	        @Value("${spring.mail.host}") String host,
	        @Value("${spring.mail.port}") int port,
	        @Value("${spring.mail.username}") String sender,
	        @Value("${spring.mail.password}") String password){

	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

	    mailSender.setHost(host);
	    mailSender.setPort(port);
	    mailSender.setUsername(sender);
	    mailSender.setPassword(password);

	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");

	    return mailSender;
	}
}
