package br.com.arenamatch.repository;

import br.com.arenamatch.entity.CategoriaTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaTimeRepository extends JpaRepository<CategoriaTime, Long> {}
