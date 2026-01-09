package br.com.arenamatch.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Table(name="time_agenda")
@Data
public class AgendaTime {

    @Id
    @SequenceGenerator(name = "sq_time_agenda_gen", sequenceName = "sq_time_agenda", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_time_agenda_gen")
    private Long id;


    @ManyToOne(optional=false)
    @JoinColumn(name="time_id", nullable=false,
            foreignKey = @ForeignKey(name="fk_time_agenda_time"))
    private Time time;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=3)
    private DiaSemana diaSemana;

    @Column(nullable=false)
    private LocalTime horaInicio;

    @Column(nullable=false)
    private LocalTime horaFim;

}
