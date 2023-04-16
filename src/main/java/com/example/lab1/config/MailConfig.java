package com.example.lab1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${mail.debug}")
    private String debug;

    @Bean
    public JavaMailSender getMailSenderYandex(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.yandex.ru");
        mailSender.setUsername(""); // тут логин твоей почты на яндексе
        mailSender.setPassword(""); //пароль от нее
        mailSender.setPort(465);

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);

        return mailSender;
    }

    @Bean
    public JavaMailSender getMailSenderGmail(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername(""); // логин от почты google
        mailSender.setPassword(""); //пароль от нее
        mailSender.setPort(465);

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);

        return mailSender;
    }
}
