package org.userservice.service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.userservice.model.UserEvent;

@Service
@RequiredArgsConstructor
public class UserServiceListen {
    private final EmailServiceImlp emailService;

    @KafkaListener(topics = "user.events", groupId = "notification-group")
    public void handleUserEvent(UserEvent event) {
        switch (event.getOperation()) {
            case CREATED:
                emailService.sendCreatedEmail(event.getEmail());
                break;
            case DELETED:
                emailService.sendDeletedEmail(event.getEmail());
                break;
        }
    }
}
