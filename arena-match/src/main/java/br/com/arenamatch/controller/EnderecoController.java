package br.com.arenamatch.controller;

import br.com.arenamatch.dto.EnderecoResponse;
import br.com.arenamatch.dto.ErroResponse;
import br.com.arenamatch.service.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<?> buscarPorCep(@PathVariable String cep) {
        try {
            EnderecoResponse resp = enderecoService.buscarEnderecoPorCep(cep);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErroResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErroResponse("Erro ao consultar CEP."));
        }
    }
}
