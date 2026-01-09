package br.com.arenamatch.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "time_liga",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_time_liga",
                        columnNames = {"time_id", "liga_id"}
                )
        }
)
@Data
public class TimeLiga {

    @Id
    @SequenceGenerator(
            name = "sq_time_liga_gen",
            sequenceName = "sq_time_liga",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sq_time_liga_gen"
    )
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "time_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_time_liga_time")
    )
    private Time time;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "liga_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_time_liga_liga")
    )
    private Liga liga;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTimeLiga status;

    @Column(name = "solicitado_em", nullable = false)
    private LocalDateTime solicitadoEm = LocalDateTime.now();

    @Column(name = "respondido_em")
    private LocalDateTime respondidoEm;

}
