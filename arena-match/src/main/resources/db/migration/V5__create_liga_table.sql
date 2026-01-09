-- V5__create_liga_table.sql

CREATE TABLE liga (
                      id BIGSERIAL PRIMARY KEY,
                      nome VARCHAR(120) NOT NULL,
                      descricao VARCHAR(255),
                      criador_representante_id BIGINT NOT NULL,
                      criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                      CONSTRAINT fk_liga_representante
                          FOREIGN KEY (criador_representante_id)
                              REFERENCES representante (id)
);
