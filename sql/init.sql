-- ============================================================
-- Script de criação do banco de dados: condominio_db
-- Sistema de Gerenciamento de Reservas de Áreas Comuns
-- ============================================================

CREATE DATABASE IF NOT EXISTS condominio_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE condominio_db;

-- ============================================================
-- Tabela: condominio
-- Armazena regras de bloqueio de dias e horários para reservas.
-- Campos booleanos indicam se o dia está BLOQUEADO.
-- Campos de horário indicam até que hora reservas são proibidas.
-- ============================================================
CREATE TABLE IF NOT EXISTS condominio (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    dom         BOOLEAN NOT NULL DEFAULT FALSE,
    seg         BOOLEAN NOT NULL DEFAULT FALSE,
    ter         BOOLEAN NOT NULL DEFAULT FALSE,
    qua         BOOLEAN NOT NULL DEFAULT FALSE,
    qui         BOOLEAN NOT NULL DEFAULT FALSE,
    sex         BOOLEAN NOT NULL DEFAULT FALSE,
    sab         BOOLEAN NOT NULL DEFAULT FALSE,
    horario_dom TIME    DEFAULT NULL,
    horario_seg TIME    DEFAULT NULL,
    horario_ter TIME    DEFAULT NULL,
    horario_qua TIME    DEFAULT NULL,
    horario_qui TIME    DEFAULT NULL,
    horario_sex TIME    DEFAULT NULL,
    horario_sab TIME    DEFAULT NULL
) ENGINE=InnoDB;

-- ============================================================
-- Tabela: moradores
-- Cadastro dos moradores do condomínio.
-- ============================================================
CREATE TABLE IF NOT EXISTS moradores (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    nome                VARCHAR(150) NOT NULL,
    numero_apartamento  VARCHAR(20)  NOT NULL,
    INDEX idx_moradores_nome (nome)
) ENGINE=InnoDB;

-- ============================================================
-- Tabela: areas_comuns
-- Cadastro das áreas comuns disponíveis para reserva.
-- ============================================================
CREATE TABLE IF NOT EXISTS areas_comuns (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    capacidade_maxima INT,
    INDEX idx_areas_comuns_nome (nome)
) ENGINE=InnoDB;

-- ============================================================
-- Tabela: reservas
-- Registro das reservas feitas pelos moradores.
-- ============================================================
CREATE TABLE IF NOT EXISTS reservas (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    morador_id       INT  NOT NULL,
    area_comum_id    INT  NOT NULL,
    data_reserva     DATE NOT NULL,
    horario_reserva  TIME NOT NULL,
    CONSTRAINT fk_reserva_morador
        FOREIGN KEY (morador_id) REFERENCES moradores(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_reserva_area_comum
        FOREIGN KEY (area_comum_id) REFERENCES areas_comuns(id)
        ON DELETE CASCADE,
    INDEX idx_reserva_data (data_reserva),
    INDEX idx_reserva_area (area_comum_id),
    INDEX idx_reserva_morador (morador_id),
    UNIQUE INDEX idx_reserva_unica (area_comum_id, data_reserva, horario_reserva)
) ENGINE=InnoDB;
