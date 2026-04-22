package com.movie.userservice.service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.movie.userservice.dto.LoginRequest;
import com.movie.userservice.dto.RegisterRequest;
import com.movie.userservice.messaging.UserEventProducer;
import com.movie.userservice.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    private final UserEventProducer userEventProducer;

    public User register(RegisterRequest request) {
        boolean existed = users.values().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(request.getEmail()));

        if (existed) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .id(idGenerator.incrementAndGet())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        users.put(user.getId(), user);
        userEventProducer.publishUserRegistered(user);
        return user;
    }

    public Optional<User> login(LoginRequest request) {
        return users.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(request.getEmail()))
                .filter(user -> user.getPassword().equals(request.getPassword()))
                .findFirst();
    }
}
