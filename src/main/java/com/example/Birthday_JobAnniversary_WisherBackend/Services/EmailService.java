package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender emailSender;

    public void sendMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sdcb0113977-4a2a66@inbox.mailtrap.io");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }
}
