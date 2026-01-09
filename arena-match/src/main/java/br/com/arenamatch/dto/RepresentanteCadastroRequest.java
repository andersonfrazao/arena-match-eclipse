package br.com.arenamatch.dto;

public record RepresentanteCadastroRequest(
        String nome,
        String cpf,
        String email,
        String senha
) {}
