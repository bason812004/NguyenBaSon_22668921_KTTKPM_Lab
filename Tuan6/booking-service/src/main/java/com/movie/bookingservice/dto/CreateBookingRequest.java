package com.movie.bookingservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class CreateBookingRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long movieId;

    @NotNull
    @Min(1)
    private Integer seatCount;

    @NotNull
    @DecimalMin("1.0")
    private Double totalPrice;
}
