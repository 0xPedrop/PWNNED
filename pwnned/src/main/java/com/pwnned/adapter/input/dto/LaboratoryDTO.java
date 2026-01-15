package com.pwnned.adapter.input.dto;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.enums.LaboratoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LaboratoryDTO(Long labId,
                            @NotBlank(message = "Title must not be blank")
                            @Size(min = 5, max = 40, message = "Title must be between 5 and 40 characters")
                            String title,

                            @NotNull(message = "Difficulty must not be blank")
                            Difficulty difficulty,

                            @NotNull(message = "Laboratory Type must not be blank")
                            LaboratoryType laboratoryType,

                            @NotNull(message = "Learning Path Id must not be blank")
                            Long learningPathId) {
    public LaboratoryDTO() {
        this(null, null, null, null, null);
    }
}
