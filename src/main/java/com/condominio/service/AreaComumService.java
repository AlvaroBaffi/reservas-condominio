package com.condominio.service;

import com.condominio.model.AreaComum;
import com.condominio.repository.AreaComumRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas regras de negócio da entidade AreaComum.
 */
public class AreaComumService {

    private final AreaComumRepository repository;

    public AreaComumService() {
        this.repository = new AreaComumRepository();
    }

    /**
     * Cadastra uma nova área comum com validação do nome.
     */
    public AreaComum cadastrar(AreaComum area) throws SQLException {
        validarNome(area);
        return repository.criar(area);
    }

    /**
     * Lista todas as áreas comuns cadastradas.
     */
    public List<AreaComum> listarTodas() throws SQLException {
        return repository.listarTodas();
    }

    /**
     * Busca uma área comum por ID.
     */
    public Optional<AreaComum> buscarPorId(int id) throws SQLException {
        return repository.buscarPorId(id);
    }

    /**
     * Atualiza uma área comum existente.
     */
    public void atualizar(AreaComum area) throws SQLException {
        Optional<AreaComum> existente = repository.buscarPorId(area.getId());
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Área comum com ID " + area.getId() + " não encontrada.");
        }
        validarNome(area);
        repository.atualizar(area);
    }

    /**
     * Remove uma área comum pelo ID.
     */
    public boolean remover(int id) throws SQLException {
        Optional<AreaComum> existente = repository.buscarPorId(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Área comum com ID " + id + " não encontrada.");
        }
        return repository.remover(id);
    }

    /**
     * Valida o campo nome da área comum.
     */
    private void validarNome(AreaComum area) {
        if (area.getNome() == null || area.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome da área comum é obrigatório.");
        }
    }
}
