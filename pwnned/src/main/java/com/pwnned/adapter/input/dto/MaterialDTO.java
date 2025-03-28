package com.pwnned.adapter.input.dto;

import com.pwnned.domain.enums.MaterialType;

public record MaterialDTO(Long materialId, String title, String author, String category, MaterialType type) {
    public MaterialDTO() {
        this(null, null, null, null, null);
    }
}
