package com.p.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	private String host  = "smtp.gmail.com";
	private Integer port = 587;
	private String username  = "appeducacional@gmail.com";
	private String password = "appeducacional!";

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost(host);
		javaMailSender.setPort(port);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);

		javaMailSender.setJavaMailProperties(getMailProperties());

		return javaMailSender;
	}
	
	private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        return properties;
    }
	@Bean
	public SimpleMailMessage templateNewUserMessage(){
		SimpleMailMessage template = new SimpleMailMessage();
		template.setFrom("pachanga@pachanga.com");
		template.setSubject("Alta de usuario en Pachanga");
		return template;
	}
	

}
