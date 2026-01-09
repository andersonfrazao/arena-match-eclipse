package br.com.arenamatch.client;

import br.com.arenamatch.dto.AutenticacaoRequest;
import br.com.arenamatch.dto.AutenticacaoResponse;
import br.com.arenamatch.dto.ErroResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class AutenticacaoClient {

    private final RestClient apiRestClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AutenticacaoClient(RestClient apiRestClient) {
        this.apiRestClient = apiRestClient;
    }

    public AutenticacaoResponse autenticar(String login, String senha) {
        try {
            return apiRestClient.post()
                    .uri("/api/autenticacao/login")
                    .body(new AutenticacaoRequest(login, senha))
                    .retrieve()
                    .body(AutenticacaoResponse.class);

        } catch (RestClientResponseException e) {
            throw new RuntimeException(extrairMensagemErro(e));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao comunicar com o servidor de autenticação.");
        }
    }

    private String extrairMensagemErro(RestClientResponseException e) {
        try {
            String body = e.getResponseBodyAsString();
            if (body != null && !body.isBlank()) {
                ErroResponse err = objectMapper.readValue(body, ErroResponse.class);
                if (err.mensagem() != null && !err.mensagem().isBlank()) {
                    return err.mensagem();
                }
            }
        } catch (Exception ignored) {}

        if (e.getStatusCode().value() == 400) return "Login ou senha inválidos.";
        return "Erro ao autenticar.";
    }
}
