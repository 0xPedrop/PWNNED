package com.pwnned.adapter.input.dto;

import com.pwnned.domain.enums.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record LearningPathDTO(
        Long learningPathId,

        @NotBlank(message = "Title must not be blank")
        @Size(min = 5, max = 40, message = "Title must be between 5 and 40 characters")
        String title,

        @NotBlank(message = "Category must not be blank")
        String category,

        @NotNull(message = "Difficulty must not be null")
        Difficulty difficulty) {
}
