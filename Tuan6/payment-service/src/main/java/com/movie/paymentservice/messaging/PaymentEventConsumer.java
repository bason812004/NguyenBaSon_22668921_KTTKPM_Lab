package com.movie.paymentservice.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.events.BookingCreatedEvent;
import com.movie.events.payload.BookingCreatedPayload;
import com.movie.paymentservice.service.PaymentProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final ObjectMapper objectMapper;
    private final PaymentProcessor paymentProcessor;
    private final PaymentEventProducer paymentEventProducer;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            dltTopicSuffix = ".dlt")
    @KafkaListener(topics = "${app.kafka.topics.bookingCreated}", groupId = "payment-service-group")
    public void consumeBookingCreated(String message) {
        BookingCreatedEvent event = parseBookingCreated(message);
        BookingCreatedPayload payload = event.getPayload();

        if (paymentProcessor.shouldTriggerTransientError()) {
            log.warn("Transient payment error for booking {}, trigger retry", payload.getBookingId());
            throw new IllegalStateException("Transient payment gateway error");
        }

        if (paymentProcessor.isPaymentSuccessful()) {
            paymentEventProducer.publishPaymentCompleted(payload, paymentProcessor.newPaymentId());
            log.info("Payment success for bookingId={}", payload.getBookingId());
        } else {
            paymentEventProducer.publishBookingFailed(payload.getBookingId(), payload.getUserId(), "Payment rejected");
            log.info("Payment failed for bookingId={}", payload.getBookingId());
        }
    }

    @KafkaListener(topics = "${app.kafka.topics.bookingCreated}.dlt", groupId = "payment-service-dlt-group")
    public void consumeBookingCreatedDlt(String message) {
        BookingCreatedEvent event = parseBookingCreated(message);
        BookingCreatedPayload payload = event.getPayload();

        log.error("Message moved to DLQ for bookingId={}: {}", payload.getBookingId(), message);
        paymentEventProducer.publishBookingFailed(
                payload.getBookingId(),
                payload.getUserId(),
                "Payment technical failure after retries");
    }

    private BookingCreatedEvent parseBookingCreated(String message) {
        try {
            return objectMapper.readValue(message, BookingCreatedEvent.class);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Cannot parse BOOKING_CREATED event: " + message, ex);
        }
    }
}
