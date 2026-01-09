package br.com.arenamatch.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "representante",
        uniqueConstraints = {
                @UniqueConstraint(name="uk_representante_cpf", columnNames = "cpf"),
                @UniqueConstraint(name="uk_representante_email", columnNames = "email")
        })
@Data
public class Representante {

    @Id
    @SequenceGenerator(name = "sq_representante_gen", sequenceName = "sq_representante", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_representante_gen")
    private Long id;

    @Column(nullable=false, length=14)
    private String cpf; // armazenar normalizado (só números)

    @Column(nullable=false, length=200)
    private String email;

    @Column(nullable=false, length=200)
    private String nome;

    @Column(name="senha_hash", nullable=false, length=200)
    private String senhaHash;

    @Column(nullable=false)
    private boolean ativo = true;

    @Column(name="data_criacao", nullable=false)
    private Instant dataCriacao = Instant.now();

    @Column(name="data_fim_trial")
    private LocalDate dataFimTrial;
 
  }
