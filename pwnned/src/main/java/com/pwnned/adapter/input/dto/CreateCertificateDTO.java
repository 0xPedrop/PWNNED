package com.pwnned.adapter.input.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateCertificateDTO(
        @NotBlank(message = "Title must not be blank")
        @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
        String title,

        @NotBlank(message = "Url must not be blank")
        String url,

        @NotNull(message = "UserId must not be null")
        UUID userId,

        @NotNull(message = "LearningPathId must not be null")
        UUID learningPathId

) {
}
