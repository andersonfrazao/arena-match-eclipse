package br.com.arenamatch.service;

import br.com.arenamatch.entity.Representante;
import br.com.arenamatch.repository.RepresentanteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CadastroRepresentanteService {

    private final RepresentanteRepository repo;
    private final PasswordEncoder encoder;

    public CadastroRepresentanteService(RepresentanteRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public Representante cadastrar(String nome, String cpf, String email, String senhaPura) {
        String cpfNorm = cpf.replaceAll("\\D", "");

        if (repo.findByCpf(cpfNorm).isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        if (repo.findByEmailIgnoreCase(email).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        Representante r = new Representante();
        r.setNome(nome);
        r.setCpf(cpfNorm);
        r.setEmail(email.trim().toLowerCase());
        r.setSenhaHash(encoder.encode(senhaPura));

        // trial (ex.: 14 dias)
        r.setDataFimTrial(LocalDate.now().plusDays(14));

        return repo.save(r);
    }
}
