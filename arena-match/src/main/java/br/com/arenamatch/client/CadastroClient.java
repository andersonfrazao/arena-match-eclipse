package br.com.arenamatch.client;

import br.com.arenamatch.dto.CadastroRequest;
import br.com.arenamatch.dto.CadastroResponse;
import br.com.arenamatch.dto.ErroResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CadastroClient {

    private final RestClient apiRestClient;

    public CadastroClient(RestClient apiRestClient) {
        this.apiRestClient = apiRestClient;
    }

    public CadastroResponse criarCadastro(CadastroRequest request) {
        try {
            return apiRestClient.post()
                    .uri("/api/cadastros")
                    .body(request)
                    .retrieve()
                    .body(CadastroResponse.class);
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("Erro ao criar cadastro: " + e.getMessage());

            // tenta ler mensagem amigável do backend
            /*try {
                ErroResponse err = apiRestClient.get().uri("about:blank").retrieve().body(ErroResponse.class);
                // (não vai funcionar assim) — então tratamos simples:
            } catch (Exception ignored) {

            }
*/
            /*// fallback
            //String msg = (e.getStatusCode().value() == 400)
                    ? "Dados inválidos. Verifique os campos."
                    : "Erro ao concluir cadastro.";
            throw new RuntimeException(msg);*/
        }
    }
}
