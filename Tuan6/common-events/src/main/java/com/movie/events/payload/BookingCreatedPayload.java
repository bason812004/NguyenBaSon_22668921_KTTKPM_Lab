package com.movie.events.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingCreatedPayload {
    private Long bookingId;
    private Long userId;
    private Long movieId;
    private Integer seatCount;
    private Double totalPrice;
}
