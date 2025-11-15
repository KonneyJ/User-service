package org.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.userservice.service.kafka.EmailService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final EmailService emailService;

    @PostMapping("/created")
    public String sendCreatedEmail(@RequestParam String email) {
        emailService.sendCreatedEmail(email);
        return "Email отправлен на " + email;
    }

    @PostMapping("/deleted")
    public String sendDeletedEmail(@RequestParam String email) {
        emailService.sendDeletedEmail(email);
        return "Email отправлен на " + email;
    }
}
