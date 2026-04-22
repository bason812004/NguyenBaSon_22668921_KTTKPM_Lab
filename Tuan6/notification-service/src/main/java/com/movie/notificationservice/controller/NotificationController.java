package com.movie.notificationservice.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @GetMapping("/notifications/health")
    public Map<String, String> health() {
        return Map.of("service", "notification-service", "status", "UP");
    }
}
