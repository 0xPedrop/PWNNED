package com.pwnned.adapter.input.dto;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.model.Laboratory;

import java.util.List;
import java.util.UUID;

public record LearningPathDTO(UUID pathId, String title, String category,
                              Difficulty difficulty, List<Laboratory> laboratories) {
    public LearningPathDTO() {
        this(null, null, null, null, null);
    }
}
