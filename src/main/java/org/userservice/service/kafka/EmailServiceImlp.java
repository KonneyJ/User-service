package org.userservice.service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImlp implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendCreatedEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Ваш аккаунт создан");
        message.setText("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
        mailSender.send(message);
    }

    @Override
    public void sendDeletedEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Аккаунт удалён");
        message.setText("Здравствуйте! Ваш аккаунт был удалён.");
        mailSender.send(message);
    }
}
