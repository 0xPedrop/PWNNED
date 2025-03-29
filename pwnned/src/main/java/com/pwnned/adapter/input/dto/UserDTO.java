package com.pwnned.adapter.input.dto;

import com.pwnned.domain.enums.UserType;

import java.util.UUID;

public record UserDTO(UUID userId, String email, String password, String username, UserType userType) {
    public UserDTO() {
        this(null, null, null, null, null);
    }
}
