package com.movie.movieservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateMovieRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String genre;

    @Min(1)
    private Integer durationMinutes;

    private String description;
}
