package com.movie.bookingservice.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.movie.bookingservice.model.EventLog;
import com.movie.bookingservice.repository.EventLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventLogService {

    private final EventLogRepository eventLogRepository;

    public void logEvent(String eventType, String payload) {
        EventLog eventLog = EventLog.builder()
                .eventType(eventType)
                .payload(payload)
                .createdAt(Instant.now())
                .build();

        eventLogRepository.save(eventLog);
    }
}
