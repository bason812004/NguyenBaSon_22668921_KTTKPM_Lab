package com.movie.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.bookingservice.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
