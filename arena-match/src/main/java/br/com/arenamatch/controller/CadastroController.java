package br.com.arenamatch.controller;

import br.com.arenamatch.dto.*;
import br.com.arenamatch.service.CadastroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cadastros")
public class CadastroController {

    private final CadastroService cadastroService;

    public CadastroController(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody CadastroRequest request) {
        try {
            CadastroResponse resp = cadastroService.criarCadastro(request);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErroResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErroResponse("Erro ao concluir cadastro."));
        }
    }
}
