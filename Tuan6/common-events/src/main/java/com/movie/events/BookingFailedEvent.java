package com.movie.events;

import com.movie.events.payload.BookingFailedPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingFailedEvent {
    private String eventId;
    private java.time.Instant timestamp;
    private BookingFailedPayload payload;
}
