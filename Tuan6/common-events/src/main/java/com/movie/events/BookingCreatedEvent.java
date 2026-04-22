package com.movie.events;

import com.movie.events.payload.BookingCreatedPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingCreatedEvent {
    private String eventId;
    private java.time.Instant timestamp;
    private BookingCreatedPayload payload;
}
