package br.com.arenamatch.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="time")
@Data
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=120)
    private String nome;

    @Column(nullable=false, length=9)
    private String cep;

    @Column(length=200)
    private String logradouro;

    @Column(length=120)
    private String bairro;

    @Column(length=120)
    private String cidade;

    @Column(length=2)
    private String uf;

    @Column(length=60)
    private String regiao;

    @Column(nullable=false)
    private boolean temCasa;

    @Column(nullable=false)
    private boolean somenteFora;

    @OneToOne(optional=false)
    @JoinColumn(name="representante_id", nullable=false,
            foreignKey = @ForeignKey(name="fk_time_representante"))
    private Representante representante;

}
