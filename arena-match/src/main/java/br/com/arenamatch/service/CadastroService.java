package br.com.arenamatch.service;

import br.com.arenamatch.dto.*;
import br.com.arenamatch.entity.*;
import br.com.arenamatch.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CadastroService {

    private final RepresentanteRepository representanteRepository;
    private final TimeRepository timeRepository;
    private final CategoriaTimeRepository categoriaTimeRepository;
    private final AgendaTimeRepository agendaTimeRepository;
    private final PasswordEncoder passwordEncoder;

    public CadastroService(RepresentanteRepository representanteRepository,
                           TimeRepository timeRepository,
                           CategoriaTimeRepository categoriaTimeRepository,
                           AgendaTimeRepository agendaTimeRepository,
                           PasswordEncoder passwordEncoder) {
        this.representanteRepository = representanteRepository;
        this.timeRepository = timeRepository;
        this.categoriaTimeRepository = categoriaTimeRepository;
        this.agendaTimeRepository = agendaTimeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CadastroResponse criarCadastro(CadastroRequest req) {
        validar(req);

        // Representante
        Representante r = new Representante();
        r.setNome(req.representante().nome().trim());
        r.setCpf(normalizarCpf(req.representante().cpf()));
        r.setEmail(req.representante().email().trim().toLowerCase());
        r.setSenhaHash(passwordEncoder.encode(req.representante().senha()));
        r.setAtivo(true);
        r.setDataFimTrial(LocalDate.now().plusDays(60));
        r = representanteRepository.save(r);

        // Regra: 1 representante = 1 time
        if (timeRepository.existsByRepresentante_Id(r.getId())) {
            throw new IllegalArgumentException("Este representante já possui um time.");
        }

        // Time
        Time t = new Time();
        t.setNome(req.time().nome().trim());
        t.setCep(normalizarCep(req.time().cep()));
        t.setLogradouro(req.time().logradouro());
        t.setBairro(req.time().bairro());
        t.setCidade(req.time().cidade());
        t.setUf(req.time().uf());
        t.setRegiao(req.time().regiao());
        t.setTemCasa(req.time().temCasa());
        t.setSomenteFora(req.time().somenteFora());
        t.setRepresentante(r);
        t = timeRepository.save(t);

        // Categorias
        for (String cat : req.categorias()) {
            CategoriaTime ct = new CategoriaTime();
            ct.setTime(t);
            ct.setCategoria(Categoria.valueOf(cat));
            categoriaTimeRepository.save(ct);
        }

        // Agenda (se tiver)
        if (req.agendas() != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
            for (AgendaRequest a : req.agendas()) {
                AgendaTime ag = new AgendaTime();
                ag.setTime(t);
                ag.setCategoria(Categoria.valueOf(a.categoria()));
                ag.setDiaSemana(DiaSemana.valueOf(a.diaSemana()));
                ag.setHoraInicio(LocalTime.parse(a.horaInicio(), fmt));
                ag.setHoraFim(LocalTime.parse(a.horaFim(), fmt));
                agendaTimeRepository.save(ag);
            }
        }

        return new CadastroResponse(r.getId(), t.getId());
    }

    private void validar(CadastroRequest req) {
        if (req == null) throw new IllegalArgumentException("Requisição inválida.");
        if (req.representante() == null) throw new IllegalArgumentException("Representante obrigatório.");
        if (req.time() == null) throw new IllegalArgumentException("Time obrigatório.");

        String cpf = normalizarCpf(req.representante().cpf());
        String email = req.representante().email() == null ? "" : req.representante().email().trim().toLowerCase();

        if (representanteRepository.existsByCpf(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        if (representanteRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        // regra temCasa x somenteFora
        if (req.time().temCasa() && req.time().somenteFora()) {
            throw new IllegalArgumentException("Escolha: tem casa OU só joga fora.");
        }

        // categorias
        if (req.categorias() == null || req.categorias().isEmpty()) {
            throw new IllegalArgumentException("Selecione pelo menos 1 categoria.");
        }
        if (req.categorias().size() > 3) {
            throw new IllegalArgumentException("Selecione no máximo 3 categorias.");
        }
        // sem duplicidade
        Set<String> unique = new HashSet<>(req.categorias());
        if (unique.size() != req.categorias().size()) {
            throw new IllegalArgumentException("Categorias duplicadas.");
        }

        // se temCasa, agenda deve existir (MVP)
        if (req.time().temCasa()) {
            if (req.agendas() == null || req.agendas().isEmpty()) {
                throw new IllegalArgumentException("Se o time tem casa, informe a agenda.");
            }
        }
    }

    private String normalizarCep(String cep) {
        String c = cep == null ? "" : cep.replaceAll("\\D", "");
        if (c.length() != 8) throw new IllegalArgumentException("CEP inválido.");
        return c.substring(0,5) + "-" + c.substring(5);
    }

    private String normalizarCpf(String cpf) {
        String c = cpf == null ? "" : cpf.replaceAll("\\D", "");
        if (c.length() != 11) throw new IllegalArgumentException("CPF inválido.");
        //var cpfNormalalizado =  c.substring(0,3) + "." + c.substring(3,6) + "." + c.substring(6,9) + "-" + c.substring(9);
        return c;
    }
}

