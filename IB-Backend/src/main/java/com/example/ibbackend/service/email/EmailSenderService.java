package com.example.ibbackend.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String activationID) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("serbia.mango@gmail.com");
        message.setTo(to);
        message.setText("Hey! Your activation code is " + activationID);
        message.setSubject("Activate your ManGo account!");
        mailSender.send(message);
    }
}
