package com.pwnned.adapter.input.dto;

import com.pwnned.domain.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserDTO(
        UUID userId,

        @NotBlank(message = "Email must not be blank")
        @Email(message = "Invalid Format for Email")
        String email,

        @NotBlank(message = "Password must not be blank")
        @Size(min = 8, max = 20,  message = "Password must be between 8 and 20 characters")
        String password,

        @NotBlank(message = "Username must not be blank")
        @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
        String username,

        UserType userType) {
    public UserDTO() {
        this(null, null, null, null, null);
    }
}
