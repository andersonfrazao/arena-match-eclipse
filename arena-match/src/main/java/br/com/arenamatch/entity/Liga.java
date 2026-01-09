package br.com.arenamatch.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "liga")
@Data
public class Liga {

    @Id
    @SequenceGenerator(
            name = "sq_liga_gen",
            sequenceName = "sq_liga",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sq_liga_gen"
    )
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "criador_representante_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_liga_representante")
    )
    private Representante criador;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();
}
