package com.pwnned.adapter.input.dto;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.enums.LabType;

import java.util.UUID;

public record LaboratoryDTO(UUID labId, String title, Difficulty difficulty, LabType labType) {
    public LaboratoryDTO() {
        this(null, null, null, null);
    }
}
