package br.com.arenamatch.client;

import br.com.arenamatch.beans.CadastroBean;
import br.com.arenamatch.dto.EnderecoDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class EnderecoClient {

    private final RestClient apiRestClient;

    public EnderecoClient(RestClient apiRestClient) {
        this.apiRestClient = apiRestClient;
    }

    public EnderecoDTO buscarPorCep(String cepSomenteNumeros) {
        return apiRestClient.get()
                .uri("/api/enderecos/cep/{cep}", cepSomenteNumeros)
                .retrieve()
                .body(EnderecoDTO.class);
    }
}
