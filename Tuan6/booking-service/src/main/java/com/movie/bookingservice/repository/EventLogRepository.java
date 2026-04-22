package com.movie.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.bookingservice.model.EventLog;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
}
