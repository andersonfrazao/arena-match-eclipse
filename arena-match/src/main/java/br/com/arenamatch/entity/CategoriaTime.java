package br.com.arenamatch.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="time_categoria",
        uniqueConstraints = @UniqueConstraint(name="uk_time_categoria", columnNames = {"time_id","categoria"}))
public class CategoriaTime {

    @Id
    @SequenceGenerator(name = "sq_time_categoria_gen", sequenceName = "sq_time_categoria", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_time_categoria_gen")
    private Long id;


    @ManyToOne(optional=false)
    @JoinColumn(name="time_id", nullable=false,
            foreignKey = @ForeignKey(name="fk_time_categoria_time"))
    private Time time;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private Categoria categoria;

}
