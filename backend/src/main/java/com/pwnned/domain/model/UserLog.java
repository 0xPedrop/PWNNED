package com.pwnned.domain.model;

import java.time.LocalDateTime;

public record UserLog(String id, String userId, String action, LocalDateTime timestamp) {
    public UserLog(String userId, String action) {
        this(null, userId, action, LocalDateTime.now());
    }
}