package com.pwnned.adapter.input.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCertificateDTO(
        @NotBlank(message = "O nome para o certificado não pode estar em branco")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String title,

        String url, 

        @NotNull(message = "UserId must not be null")
        Long userId,

        @NotNull(message = "LearningPathId must not be null")
        Long learningPathId
) {
}
