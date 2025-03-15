package com.deremate.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.deremate.demo.service.Interface.MailService;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendConfirmationMail(String recipient, String code) {
        String subject = "Email Verification Code";
        String body = "Your verification code is: " + code
                + ". Please enter it in the app to confirm your registration.";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("deremateg10@gmail.com");

        mailSender.send(message);
    }


}

/*
 * public String generateVerificationCode() {
 * Random random = new Random();
 * int code = 100000 + random.nextInt(900000); // Ensures a 6-digit code
 * return String.valueOf(code);
 * }
 * 
 * public void sendVerificationEmail(String recipient, String code) {
 * String subject = "Email Verification Code";
 * String body = "Your verification code is: " + code +
 * ". Please enter it in the app to confirm your registration.";
 * 
 * SimpleMailMessage message = new SimpleMailMessage();
 * message.setTo(recipient);
 * message.setSubject(subject);
 * message.setText(body);
 * message.setFrom("your_email@gmail.com");
 * 
 * mailSender.send(message);
 * }
 */