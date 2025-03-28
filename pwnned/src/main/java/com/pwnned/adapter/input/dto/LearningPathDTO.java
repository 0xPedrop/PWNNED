package com.pwnned.adapter.input.dto;

import com.pwnned.domain.enums.Difficulty;

public record LearningPathDTO(Long pathId, String title, String description, String category,
                              Difficulty difficulty) {
    public LearningPathDTO() {
        this(null, null, null, null, null);
    }
}
