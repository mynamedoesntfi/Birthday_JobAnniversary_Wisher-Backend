package com.example.Birthday_JobAnniversary_WisherBackend.Services;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmailService {

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSender emailSender;

    public void celebrationInvites(String subject, String text) {

        Set<?> emails= userService.getAllEmailsWithInMonthEvents();

        Object[] objects = emails.toArray();
        String[] recepients=Arrays.copyOf(objects, objects.length,String[].class);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("springboot.dummy.test.email@gmail.com");
        message.setTo(recepients);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }

    public void sendEmails(List<SimpleMailMessage> messages) throws Exception{
        for (SimpleMailMessage message :
                messages) {
            try {
                emailSender.send(message);
            } catch(Exception e){
                throw new Exception(e.getMessage());
            }
        }
    }
}
