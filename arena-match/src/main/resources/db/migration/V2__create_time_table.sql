-- V2__create_time_table.sql
CREATE TABLE time (
                      id BIGSERIAL PRIMARY KEY,
                      nome VARCHAR(120) NOT NULL,
                      cep VARCHAR(9) NOT NULL,
                      logradouro VARCHAR(200),
                      bairro VARCHAR(120),
                      cidade VARCHAR(120),
                      uf CHAR(2),
                      regiao VARCHAR(60),
                      tem_casa BOOLEAN NOT NULL,
                      somente_fora BOOLEAN NOT NULL,

                      representante_id BIGINT NOT NULL,

                      CONSTRAINT fk_time_representante
                          FOREIGN KEY (representante_id)
                              REFERENCES representante (id),

                      CONSTRAINT uk_time_representante
                          UNIQUE (representante_id)
);
