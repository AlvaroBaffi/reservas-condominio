package com.condominio.service;

import com.condominio.model.Morador;
import com.condominio.repository.MoradorRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas regras de negócio da entidade Morador.
 */
public class MoradorService {

    private final MoradorRepository repository;

    public MoradorService() {
        this.repository = new MoradorRepository();
    }

    /**
     * Cadastra um novo morador com validações básicas.
     */
    public Morador cadastrar(Morador morador) throws SQLException {
        validarCampos(morador);
        return repository.criar(morador);
    }

    /**
     * Lista todos os moradores cadastrados.
     */
    public List<Morador> listarTodos() throws SQLException {
        return repository.listarTodos();
    }

    /**
     * Busca um morador por ID.
     */
    public Optional<Morador> buscarPorId(int id) throws SQLException {
        return repository.buscarPorId(id);
    }

    /**
     * Atualiza os dados de um morador existente.
     */
    public void atualizar(Morador morador) throws SQLException {
        Optional<Morador> existente = repository.buscarPorId(morador.getId());
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Morador com ID " + morador.getId() + " não encontrado.");
        }
        validarCampos(morador);
        repository.atualizar(morador);
    }

    /**
     * Remove um morador pelo ID.
     */
    public boolean remover(int id) throws SQLException {
        Optional<Morador> existente = repository.buscarPorId(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Morador com ID " + id + " não encontrado.");
        }
        return repository.remover(id);
    }

    /**
     * Valida campos obrigatórios do morador.
     */
    private void validarCampos(Morador morador) {
        if (morador.getNome() == null || morador.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do morador é obrigatório.");
        }
        if (morador.getNumeroApartamento() == null || morador.getNumeroApartamento().isBlank()) {
            throw new IllegalArgumentException("O número do apartamento é obrigatório.");
        }
    }
}
