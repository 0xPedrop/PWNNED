package com.pwnned.adapter.input.dto;

import com.pwnned.domain.enums.Difficulty;

public record LaboratoryDTO(Long labId, String title, Difficulty difficulty, String instructions,
                            String videoLesson) {
    public LaboratoryDTO() {
        this(null, null, null, null, null);
    }
}
