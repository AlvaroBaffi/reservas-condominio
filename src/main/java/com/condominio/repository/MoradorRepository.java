package com.condominio.repository;

import com.condominio.config.DatabaseConnection;
import com.condominio.model.Morador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório responsável pelas operações de banco de dados da entidade Morador.
 */
public class MoradorRepository {

    /**
     * Insere um novo morador no banco.
     */
    public Morador criar(Morador morador) throws SQLException {
        String sql = "INSERT INTO moradores (nome, numero_apartamento) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, morador.getNome());
            ps.setString(2, morador.getNumeroApartamento());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    morador.setId(rs.getInt(1));
                }
            }
        }
        return morador;
    }

    /**
     * Lista todos os moradores cadastrados.
     */
    public List<Morador> listarTodos() throws SQLException {
        String sql = "SELECT * FROM moradores ORDER BY nome";
        List<Morador> lista = new ArrayList<>();

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
     * Busca um morador pelo ID.
     */
    public Optional<Morador> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM moradores WHERE id = ?";

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
     * Atualiza os dados de um morador.
     */
    public void atualizar(Morador morador) throws SQLException {
        String sql = "UPDATE moradores SET nome = ?, numero_apartamento = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, morador.getNome());
            ps.setString(2, morador.getNumeroApartamento());
            ps.setInt(3, morador.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Remove um morador pelo ID.
     */
    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM moradores WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ======================== Método auxiliar ========================

    private Morador mapear(ResultSet rs) throws SQLException {
        Morador m = new Morador();
        m.setId(rs.getInt("id"));
        m.setNome(rs.getString("nome"));
        m.setNumeroApartamento(rs.getString("numero_apartamento"));
        return m;
    }
}
