package br.com.arenamatch.dto;

public record EnderecoResponse(
        String cep,
        String logradouro,
        String bairro,
        String cidade,
        String uf,
        String regiao
) {}
