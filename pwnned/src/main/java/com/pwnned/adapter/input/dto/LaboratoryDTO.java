package com.pwnned.adapter.input.dto;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.enums.LaboratoryType;

import java.util.UUID;

public record LaboratoryDTO(UUID labId, String title, Difficulty difficulty, LaboratoryType laboratoryType, UUID learningPathId) {
    public LaboratoryDTO() {
        this(null, null, null, null, null);
    }
}
