package com.condominio.service;

import com.condominio.model.Condominio;
import com.condominio.repository.CondominioRepository;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Serviço responsável pelas regras de negócio da entidade Condominio.
 */
public class CondominioService {

    private final CondominioRepository repository;

    public CondominioService() {
        this.repository = new CondominioRepository();
    }

    /**
     * Cria um novo registro de condomínio.
     * Apenas um registro é permitido por banco.
     */
    public Condominio criar(Condominio condominio) throws SQLException {
        Optional<Condominio> existente = repository.buscar();
        if (existente.isPresent()) {
            throw new IllegalStateException("Já existe um condomínio cadastrado. Use a opção de atualizar.");
        }
        return repository.criar(condominio);
    }

    /**
     * Atualiza as regras de dias e horários do condomínio.
     */
    public void atualizar(Condominio condominio) throws SQLException {
        Optional<Condominio> existente = repository.buscarPorId(condominio.getId());
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Condomínio com ID " + condominio.getId() + " não encontrado.");
        }
        repository.atualizar(condominio);
    }

    /**
     * Busca os dados do condomínio cadastrado.
     */
    public Optional<Condominio> buscar() throws SQLException {
        return repository.buscar();
    }
}
