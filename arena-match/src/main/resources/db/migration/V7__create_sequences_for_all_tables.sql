-- V7__create_sequences_for_all_tables.sql
-- Cria sequences e configura DEFAULT nextval() para PKs

-- =========================
-- REPRESENTANTE
-- =========================
CREATE SEQUENCE IF NOT EXISTS sq_representante
    INCREMENT 1
    START 1
    MINVALUE 1;


-- =========================
-- TIME
-- =========================
CREATE SEQUENCE IF NOT EXISTS sq_time
    INCREMENT 1
    START 1
    MINVALUE 1;


-- =========================
-- TIME_CATEGORIA
-- =========================
CREATE SEQUENCE IF NOT EXISTS sq_time_categoria
    INCREMENT 1
    START 1
    MINVALUE 1;


-- =========================
-- TIME_AGENDA
-- =========================
CREATE SEQUENCE IF NOT EXISTS sq_time_agenda
    INCREMENT 1
    START 1
    MINVALUE 1;

-- =========================
-- LIGA
-- =========================
CREATE SEQUENCE IF NOT EXISTS sq_liga
    INCREMENT 1
    START 1
    MINVALUE 1;


-- =========================
-- TIME_LIGA
-- =========================
CREATE SEQUENCE IF NOT EXISTS sq_time_liga
    INCREMENT 1
    START 1
    MINVALUE 1;
