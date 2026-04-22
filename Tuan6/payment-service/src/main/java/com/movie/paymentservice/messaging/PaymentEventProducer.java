package com.movie.paymentservice.messaging;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.events.BookingFailedEvent;
import com.movie.events.PaymentCompletedEvent;
import com.movie.events.payload.BookingCreatedPayload;
import com.movie.events.payload.BookingFailedPayload;
import com.movie.events.payload.PaymentCompletedPayload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.kafka.topics.paymentCompleted}")
    private String paymentCompletedTopic;

    @Value("${app.kafka.topics.bookingFailed}")
    private String bookingFailedTopic;

    public void publishPaymentCompleted(BookingCreatedPayload payload, String paymentId) {
        PaymentCompletedEvent event = PaymentCompletedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .payload(PaymentCompletedPayload.builder()
                        .bookingId(payload.getBookingId())
                        .userId(payload.getUserId())
                        .paymentId(paymentId)
                        .amount(payload.getTotalPrice())
                        .build())
                .build();

        send(event, paymentCompletedTopic, String.valueOf(payload.getBookingId()), "PAYMENT_COMPLETED");
    }

    public void publishBookingFailed(Long bookingId, Long userId, String reason) {
        BookingFailedEvent event = BookingFailedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .payload(BookingFailedPayload.builder()
                        .bookingId(bookingId)
                        .userId(userId)
                        .reason(reason)
                        .build())
                .build();

        send(event, bookingFailedTopic, String.valueOf(bookingId), "BOOKING_FAILED");
    }

    private void send(Object event, String topic, String key, String eventName) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topic, key, json);
            log.info("Published {} event to topic {}: {}", eventName, topic, json);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Failed to serialize " + eventName + " event", ex);
        }
    }
}
