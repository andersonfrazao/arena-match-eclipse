package br.com.arenamatch.service;

import br.com.arenamatch.dto.LoginRequest;
import br.com.arenamatch.dto.LoginResponse;
import br.com.arenamatch.entity.Representante;
import br.com.arenamatch.repository.RepresentanteRepository;
import br.com.arenamatch.repository.TimeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AutenticacaoService {

    private final RepresentanteRepository representanteRepository;
    private final TimeRepository timeRepository;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoService(RepresentanteRepository representanteRepository,
                               TimeRepository timeRepository,
                               PasswordEncoder passwordEncoder) {
        this.representanteRepository = representanteRepository;
        this.timeRepository = timeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse autenticar(LoginRequest req) {
        if (req == null || isBlank(req.login()) || isBlank(req.senha())) {
            throw new IllegalArgumentException("Login e senha são obrigatórios.");
        }

        String login = req.login().trim();
        Representante r = buscarPorCpfOuEmail(login);

        if (!r.isAtivo()) {
            throw new IllegalArgumentException("Usuário inativo. Entre em contato com o suporte.");
        }

        // valida trial (se estiver preenchido)
        if (r.getDataFimTrial() != null && LocalDate.now().isAfter(r.getDataFimTrial())) {
            throw new IllegalArgumentException("Seu período de teste expirou.");
        }

        if (!passwordEncoder.matches(req.senha(), r.getSenhaHash())) {
            throw new IllegalArgumentException("Login ou senha inválidos.");
        }

        // 1 representante = 1 time (se já existe)
      /*  Long timeId = timeRepository.findAll().stream() // simples; trocamos por query abaixo
                .filter(t -> t.getRepresentante().getId().equals(r.getId()))
                .map(t -> t.getId())
                .findFirst()
                .orElse(null);*/
        Long timeId = timeRepository.findByRepresentante_Id(r.getId())
                .map(t -> t.getId())
                .orElse(null);


        return new LoginResponse(r.getId(), r.getNome(), timeId);
    }

    private Representante buscarPorCpfOuEmail(String login) {
        String cpfNumeros = login.replaceAll("\\D", "");
        if (cpfNumeros.length() == 11) {
            String cpfFmt = cpfNumeros.substring(0,3) + "." + cpfNumeros.substring(3,6) + "." + cpfNumeros.substring(6,9) + "-" + cpfNumeros.substring(9);
            return representanteRepository.findByCpf(cpfFmt)
                    .orElseThrow(() -> new IllegalArgumentException("Login ou senha inválidos."));
        }

        String email = login.toLowerCase();
        return representanteRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Login ou senha inválidos."));
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
