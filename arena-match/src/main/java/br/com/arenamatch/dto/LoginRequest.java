package br.com.arenamatch.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String login, // cpf ou email
        @NotBlank String senha
) {}
