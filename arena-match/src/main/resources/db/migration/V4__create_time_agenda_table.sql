-- V4__create_time_agenda_table.sql

CREATE TABLE time_agenda (
                             id BIGSERIAL PRIMARY KEY,
                             time_id BIGINT NOT NULL,
                             categoria VARCHAR(20) NOT NULL,
                             dia_semana VARCHAR(3) NOT NULL,
                             hora_inicio TIME NOT NULL,
                             hora_fim TIME NOT NULL,

                             CONSTRAINT fk_time_agenda_time
                                 FOREIGN KEY (time_id)
                                     REFERENCES time (id)
);
