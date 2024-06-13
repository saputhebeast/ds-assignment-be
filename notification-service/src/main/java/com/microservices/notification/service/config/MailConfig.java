package com.microservices.notification.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${notification.mail.password}")
    private String password;

    @Value("${notification.mail.username}")
    private String username;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Set mail server properties
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);  // Example port, adjust as needed
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        // Set additional properties if required
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // Set to true for debugging

        return mailSender;
    }
}
