package com.pwnned.adapter.input.dto;

import com.pwnned.domain.enums.Difficulty;

import java.util.UUID;

public record LearningPathDTO(UUID learningPathId, String title, String category, Difficulty difficulty) {
}
