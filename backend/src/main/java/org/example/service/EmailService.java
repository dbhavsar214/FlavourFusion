package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        System.out.println("Sending");
        emailSender.send(message);
        System.out.println("Sent");
    }

    public void sendBulkMessage(List<String> to, String subject, String text) {
        System.out.println("going to send now to : "+to.toString());
        for (String recipient : to) {
            sendSimpleMessage(recipient, subject, text);
        }
    }
}
