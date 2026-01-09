package br.com.arenamatch.service;

import br.com.arenamatch.entity.Representante;
import br.com.arenamatch.repository.RepresentanteRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class RepresentanteUserDetailsService implements UserDetailsService {

    private final RepresentanteRepository repo;

    public RepresentanteUserDetailsService(RepresentanteRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String u = username == null ? "" : username.trim();

        Representante r;
        if (u.contains("@")) {
            r = repo.findByEmailIgnoreCase(u).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        } else {
            String cpf = u.replaceAll("\\D", "");
            r = repo.findByCpf(cpf).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        }

        if (!r.isAtivo()) {
            throw new DisabledException("Usuário inativo");
        }

        return User.withUsername(u)
                .password(r.getSenhaHash())
                .roles("USER")
                .build();
    }
}
