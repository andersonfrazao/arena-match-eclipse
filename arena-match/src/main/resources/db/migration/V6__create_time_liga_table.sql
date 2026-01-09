-- V6__create_time_liga_table.sql
CREATE TABLE time_liga (
                           id BIGSERIAL PRIMARY KEY,
                           time_id BIGINT NOT NULL,
                           liga_id BIGINT NOT NULL,
                           status VARCHAR(20) NOT NULL,
                           solicitado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           respondido_em TIMESTAMP,

                           CONSTRAINT fk_time_liga_time
                               FOREIGN KEY (time_id)
                                   REFERENCES time (id),

                           CONSTRAINT fk_time_liga_liga
                               FOREIGN KEY (liga_id)
                                   REFERENCES liga (id),

                           CONSTRAINT uk_time_liga
                               UNIQUE (time_id, liga_id)
);
