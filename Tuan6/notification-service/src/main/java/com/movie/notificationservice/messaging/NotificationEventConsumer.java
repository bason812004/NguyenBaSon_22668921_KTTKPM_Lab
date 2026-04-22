package com.movie.notificationservice.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.events.PaymentCompletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${app.kafka.topics.paymentCompleted}", groupId = "notification-service-group")
    public void consumePaymentCompleted(String message) {
        try {
            PaymentCompletedEvent event = objectMapper.readValue(message, PaymentCompletedEvent.class);
            log.info(
                    "User {} đã đặt đơn #{} thành công",
                    event.getPayload().getUserId(),
                    event.getPayload().getBookingId());
        } catch (JsonProcessingException ex) {
            log.error("Cannot parse PAYMENT_COMPLETED event: {}", message, ex);
        }
    }
}
