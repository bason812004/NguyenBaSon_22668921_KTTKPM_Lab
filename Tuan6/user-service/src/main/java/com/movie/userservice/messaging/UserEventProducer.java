package com.movie.userservice.messaging;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.events.UserRegisteredEvent;
import com.movie.events.payload.UserRegisteredPayload;
import com.movie.userservice.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.kafka.topics.userRegistered}")
    private String userRegisteredTopic;

    public void publishUserRegistered(User user) {
        UserRegisteredEvent event = UserRegisteredEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .payload(UserRegisteredPayload.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build())
                .build();

        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(userRegisteredTopic, String.valueOf(user.getId()), json);
            log.info("Published USER_REGISTERED event: {}", json);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Failed to serialize USER_REGISTERED event", ex);
        }
    }
}
