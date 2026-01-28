package com.pwnned.adapter.input.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
    @NotBlank(message = "O email ou username é obrigatório")
    String email, 

    @NotBlank(message = "A senha é obrigatória")
    String password
) {}