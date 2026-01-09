-- V3__create_time_categoria_table.sql
CREATE TABLE time_categoria (
                                id BIGSERIAL PRIMARY KEY,
                                time_id BIGINT NOT NULL,
                                categoria VARCHAR(20) NOT NULL,

                                CONSTRAINT fk_time_categoria_time
                                    FOREIGN KEY (time_id)
                                        REFERENCES time (id),

                                CONSTRAINT uk_time_categoria
                                    UNIQUE (time_id, categoria)
);
