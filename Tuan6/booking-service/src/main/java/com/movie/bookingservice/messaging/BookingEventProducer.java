package com.movie.bookingservice.messaging;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.bookingservice.model.Booking;
import com.movie.events.BookingCreatedEvent;
import com.movie.events.payload.BookingCreatedPayload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.kafka.topics.bookingCreated}")
    private String bookingCreatedTopic;

    public String publishBookingCreated(Booking booking) {
        BookingCreatedEvent event = BookingCreatedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .payload(BookingCreatedPayload.builder()
                        .bookingId(booking.getId())
                        .userId(booking.getUserId())
                        .movieId(booking.getMovieId())
                        .seatCount(booking.getSeatCount())
                        .totalPrice(booking.getTotalPrice())
                        .build())
                .build();

        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(bookingCreatedTopic, String.valueOf(booking.getId()), json);
            log.info("Published BOOKING_CREATED event: {}", json);
            return json;
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Failed to serialize BOOKING_CREATED event", ex);
        }
    }
}
