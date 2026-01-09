package br.com.arenamatch.dto;

public record TimeCadastroRequest(
        String nome,
        String cep,
        String logradouro,
        String bairro,
        String cidade,
        String uf,
        String regiao,
        boolean temCasa,
        boolean somenteFora
) {}
