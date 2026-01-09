package br.com.arenamatch.client;

import br.com.arenamatch.dto.EnderecoResponse;
import br.com.arenamatch.dto.ViaCepResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ViaCepClient {

    private final RestClient restClient;

    public ViaCepClient() {
        // Simples e direto (pode evoluir para config central depois)
        this.restClient = RestClient.builder()
                .baseUrl("https://viacep.com.br")
                .build();
    }

    public EnderecoResponse buscarPorCep(String cepSomenteNumeros) {
        ViaCepResponse resp = restClient.get()
                .uri("/ws/{cep}/json/", cepSomenteNumeros)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ViaCepResponse.class);

        if (resp == null || Boolean.TRUE.equals(resp.erro())) {
            return null;
        }

        // regiao: no ViaCEP não vem pronto, então deixamos null
        return new EnderecoResponse(
                resp.cep(),
                resp.logradouro(),
                resp.bairro(),
                resp.localidade(),
                resp.uf(),
                resp.regiao()
        );
    }

}
