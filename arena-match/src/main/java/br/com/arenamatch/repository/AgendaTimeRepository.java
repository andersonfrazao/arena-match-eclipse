package br.com.arenamatch.repository;

import br.com.arenamatch.entity.AgendaTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaTimeRepository extends JpaRepository<AgendaTime, Long> {}
