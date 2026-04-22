package com.movie.bookingservice.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.movie.bookingservice.dto.CreateBookingRequest;
import com.movie.bookingservice.messaging.BookingEventProducer;
import com.movie.bookingservice.model.Booking;
import com.movie.bookingservice.model.BookingStatus;
import com.movie.bookingservice.repository.BookingRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingApplicationService {

    private final BookingRepository bookingRepository;
    private final BookingEventProducer bookingEventProducer;
    private final EventLogService eventLogService;

    @Transactional
    public Booking createBooking(CreateBookingRequest request) {
        Instant now = Instant.now();

        Booking booking = Booking.builder()
                .userId(request.getUserId())
                .movieId(request.getMovieId())
                .seatCount(request.getSeatCount())
                .totalPrice(request.getTotalPrice())
                .status(BookingStatus.PENDING)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Booking saved = bookingRepository.save(booking);
        String payload = bookingEventProducer.publishBookingCreated(saved);
        eventLogService.logEvent("BOOKING_CREATED", payload);
        return saved;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional
    public void markBookingConfirmed(Long bookingId, String eventPayload) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setUpdatedAt(Instant.now());
        bookingRepository.save(booking);
        eventLogService.logEvent("PAYMENT_COMPLETED", eventPayload);
    }

    @Transactional
    public void markBookingFailed(Long bookingId, String eventPayload) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));

        booking.setStatus(BookingStatus.FAILED);
        booking.setUpdatedAt(Instant.now());
        bookingRepository.save(booking);
        eventLogService.logEvent("BOOKING_FAILED", eventPayload);
    }
}
