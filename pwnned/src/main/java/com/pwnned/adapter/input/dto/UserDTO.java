package com.pwnned.adapter.input.dto;

import java.util.UUID;

public record UserDTO(UUID userId, String email, String password, String username) {
    public UserDTO() {
        this(null, null, null, null); // Inicializa os campos com valores padrão
    }
}
