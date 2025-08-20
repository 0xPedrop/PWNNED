package com.pwnned.adapter.input.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupDTO(

        @NotBlank
        @Email(message = "Invalid Format for Email")
        String email,

        @NotBlank
        @Size(min = 8, max = 20,  message = "Password must be between 8 and 20 characters")
        String password,

        @NotBlank
        @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
        String username) {
}
