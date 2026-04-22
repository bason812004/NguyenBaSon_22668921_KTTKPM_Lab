package com.movie.paymentservice.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @GetMapping("/payments/health")
    public Map<String, String> health() {
        return Map.of("service", "payment-service", "status", "UP");
    }
}
