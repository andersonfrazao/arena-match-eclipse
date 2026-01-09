package br.com.arenamatch.service;

import br.com.arenamatch.client.ViaCepClient;
import br.com.arenamatch.dto.EnderecoResponse;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final ViaCepClient viaCepClient;

    public EnderecoService(ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    public EnderecoResponse buscarEnderecoPorCep(String cep) {
        String cepNumeros = normalizarCep(cep);

        EnderecoResponse resp = viaCepClient.buscarPorCep(cepNumeros);
        if (resp == null) {
            throw new IllegalArgumentException("CEP não encontrado.");
        }

        // aqui no futuro você pode mapear "região" com base na cidade/bairro,
        // ou manter como null e deixar o usuário preencher.
        return resp;
    }

    private String normalizarCep(String cep) {
        String c = cep == null ? "" : cep.replaceAll("\\D", "");
        if (c.length() != 8) {
            throw new IllegalArgumentException("CEP inválido.");
        }
        return c;
    }
}
