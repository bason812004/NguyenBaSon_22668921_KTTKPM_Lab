package com.movie.events.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedPayload {
    private Long bookingId;
    private Long userId;
    private String paymentId;
    private Double amount;
}
