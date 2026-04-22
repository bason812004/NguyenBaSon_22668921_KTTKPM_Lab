package com.movie.paymentservice.service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class PaymentProcessor {

    public boolean shouldTriggerTransientError() {
        return ThreadLocalRandom.current().nextDouble() < 0.10;
    }

    public boolean isPaymentSuccessful() {
        return ThreadLocalRandom.current().nextDouble() < 0.70;
    }

    public String newPaymentId() {
        return "PAY-" + UUID.randomUUID();
    }
}
