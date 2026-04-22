package com.movie.events;

import com.movie.events.payload.PaymentCompletedPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedEvent {
    private String eventId;
    private java.time.Instant timestamp;
    private PaymentCompletedPayload payload;
}
