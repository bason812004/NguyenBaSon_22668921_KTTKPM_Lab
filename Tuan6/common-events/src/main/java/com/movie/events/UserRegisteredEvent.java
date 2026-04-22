package com.movie.events;

import com.movie.events.payload.UserRegisteredPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEvent {
    private String eventId;
    private java.time.Instant timestamp;
    private UserRegisteredPayload payload;
}
