package org.userservice.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendCreatedEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Ваш аккаунт создан");
        message.setText("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
        mailSender.send(message);
    }

    public void sendDeletedEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Аккаунт удалён");
        message.setText("Здравствуйте! Ваш аккаунт был удалён.");
        mailSender.send(message);
    }
}
