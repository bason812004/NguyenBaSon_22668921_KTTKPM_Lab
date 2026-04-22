package com.movie.bookingservice.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.bookingservice.service.BookingApplicationService;
import com.movie.events.BookingFailedEvent;
import com.movie.events.PaymentCompletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final ObjectMapper objectMapper;
    private final BookingApplicationService bookingApplicationService;

    @Value("${app.kafka.topics.paymentCompleted}")
    private String paymentCompletedTopic;

    @Value("${app.kafka.topics.bookingFailed}")
    private String bookingFailedTopic;

    @KafkaListener(topics = "${app.kafka.topics.paymentCompleted}", groupId = "booking-service-group")
    public void consumePaymentCompleted(String message) {
        try {
            PaymentCompletedEvent event = objectMapper.readValue(message, PaymentCompletedEvent.class);
            bookingApplicationService.markBookingConfirmed(event.getPayload().getBookingId(), message);
            log.info("Consumed PAYMENT_COMPLETED from topic {}: {}", paymentCompletedTopic, message);
        } catch (JsonProcessingException ex) {
            log.error("Failed to parse PAYMENT_COMPLETED event: {}", message, ex);
        }
    }

    @KafkaListener(topics = "${app.kafka.topics.bookingFailed}", groupId = "booking-service-group")
    public void consumeBookingFailed(String message) {
        try {
            BookingFailedEvent event = objectMapper.readValue(message, BookingFailedEvent.class);
            bookingApplicationService.markBookingFailed(event.getPayload().getBookingId(), message);
            log.info("Consumed BOOKING_FAILED from topic {}: {}", bookingFailedTopic, message);
        } catch (JsonProcessingException ex) {
            log.error("Failed to parse BOOKING_FAILED event: {}", message, ex);
        }
    }
}
