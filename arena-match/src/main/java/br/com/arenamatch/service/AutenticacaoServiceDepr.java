package br.com.arenamatch.service;

import br.com.arenamatch.dto.CadastroRequest;
import br.com.arenamatch.dto.LoginRequest;
import br.com.arenamatch.dto.LoginResponse;
import br.com.arenamatch.entity.Representante;
import br.com.arenamatch.repository.RepresentanteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AutenticacaoServiceDepr {
/*
    private final RepresentanteRepository repo;
    private final PasswordEncoder encoder;

    public AutenticacaoServiceDepr(RepresentanteRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Transactional
    public LoginResponse cadastrar(CadastroRequest req) {
        String cpf = normalizarCpf(req.cpf());
        String email = normalizarEmail(req.email());

        if (repo.findByCpf(cpf).isPresent()) throw new IllegalArgumentException("CPF já cadastrado.");
        if (repo.findByEmailIgnoreCase(email).isPresent()) throw new IllegalArgumentException("E-mail já cadastrado.");

        Representante r = new Representante();
        r.setNome(req.nomeRepresentante().trim());
        r.setCpf(cpf);
        r.setEmail(email);
        r.setSenhaHash(encoder.encode(req.senha()));
        r.setDataFimTrial(LocalDate.now().plusDays(14)); // você pode parametrizar

        Representante salvo = repo.save(r);

        return new LoginResponse(salvo.getId(), null, null, salvo.getDataFimTrial());
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest req) {
        String login = req.login().trim();

        Representante r = login.contains("@")
                ? repo.findByEmailIgnoreCase(normalizarEmail(login)).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."))
                : repo.findByCpf(normalizarCpf(login)).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        if (!r.isAtivo()) throw new IllegalArgumentException("Usuário inativo.");

        if (!encoder.matches(req.senha(), r.getSenhaHash())) {
            throw new IllegalArgumentException("Senha inválida.");
        }

        return new LoginResponse(r.getId(), null, null, r.getDataFimTrial());
    }

    private String normalizarCpf(String cpf) {
        String c = cpf == null ? "" : cpf.replaceAll("\\D", "");
        if (c.length() != 11) throw new IllegalArgumentException("CPF inválido.");
        return c;
    }

    private String normalizarEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }*/
}
