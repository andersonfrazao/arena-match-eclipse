package br.com.arenamatch.repository;

import br.com.arenamatch.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {
    boolean existsByRepresentante_Id(Long representanteId);
    Optional<Time> findByRepresentante_Id(Long representanteId);
}
