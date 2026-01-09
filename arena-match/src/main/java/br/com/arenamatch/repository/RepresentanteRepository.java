package br.com.arenamatch.repository;

import br.com.arenamatch.entity.Representante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepresentanteRepository extends JpaRepository<Representante, Long> {
    Optional<Representante> findByCpf(String cpf);
    Optional<Representante> findByEmailIgnoreCase(String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
