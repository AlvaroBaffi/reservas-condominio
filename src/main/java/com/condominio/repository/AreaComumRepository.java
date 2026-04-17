package com.condominio.repository;

import com.condominio.config.DatabaseConnection;
import com.condominio.model.AreaComum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório responsável pelas operações de banco de dados da entidade AreaComum.
 */
public class AreaComumRepository {

    /**
     * Insere uma nova área comum no banco.
     */
    public AreaComum criar(AreaComum area) throws SQLException {
        String sql = "INSERT INTO areas_comuns (nome, capacidade_maxima) VALUES (?, ?)"; //adiciona o valor de capacidade maxima na query

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, area.getNome());
            ps.setInt(2,area.getCapacidadeMaxima());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    area.setId(rs.getInt(1));
                }
            }
        }
        return area;
    }

    /**
     * Lista todas as áreas comuns cadastradas.
     */
    public List<AreaComum> listarTodas() throws SQLException {
        String sql = "SELECT * FROM areas_comuns ORDER BY nome, capacidade_maxima"; //adiciona capacidade maxima ao fazer consulta na hora de listar
        List<AreaComum> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /**
     * Busca uma área comum pelo ID.
     */
    public Optional<AreaComum> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM areas_comuns WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Atualiza os dados de uma área comum.
     */
    public void atualizar(AreaComum area) throws SQLException {
        String sql = "UPDATE areas_comuns SET nome = ?, capacidade_maxima = ? WHERE id = ?"; //adiciona o valor de capacidade maxima para atualizar, caso o usuario adicione um valor

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, area.getNome());
            ps.setInt(2,area.getCapacidadeMaxima()); // getters e setters da capacidade maxima para dentro da consulta
            ps.setInt(3, area.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Remove uma área comum pelo ID.
     */
    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM areas_comuns WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ======================== Método auxiliar ========================

    private AreaComum mapear(ResultSet rs) throws SQLException {
        AreaComum a = new AreaComum();
        a.setId(rs.getInt("id"));
        a.setNome(rs.getString("nome"));
        a.setCapacidadeMaxima(rs.getInt("capacidade_maxima")); // setter da capacidade maxima dentro do metodo auxiliar
        return a;
    }
}
