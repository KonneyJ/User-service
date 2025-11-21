package org.userservice.service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.userservice.model.Operation;
import org.userservice.model.UserEvent;

@Service
@RequiredArgsConstructor
public class UserServicePublish {
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public void publishUserCreated(String email) {
        UserEvent event = new UserEvent(Operation.CREATED, email);
        kafkaTemplate.send("user.events", event);
    }

    public void publishUserDeleted(String email) {
        UserEvent event = new UserEvent(Operation.DELETED, email);
        kafkaTemplate.send("user.events", event);
    }
}
