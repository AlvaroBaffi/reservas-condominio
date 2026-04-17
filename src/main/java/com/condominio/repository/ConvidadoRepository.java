package com.condominio.repository;

import com.condominio.config.DatabaseConnection;
import com.condominio.model.Convidado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório responsável pelas operações de banco de dados da entidade Convidado.
 */
public class ConvidadoRepository {

    /**
     * Insere um novo convidado no banco.
     */
    public Convidado criar(Convidado convidado) throws SQLException {
        String sql = "INSERT INTO convidados (reserva_id, nome, cpf, rg) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, convidado.getReservaId());
            ps.setString(2, convidado.getNome());
            ps.setString(3, convidado.getCpf());
            ps.setString(4, convidado.getRg());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    convidado.setId(rs.getInt(1));
                }
            }
        }
        return convidado;
    }

    /**
     * Lista todos os convidados de uma reserva.
     */
    public List<Convidado> listarPorReserva(int reservaId) throws SQLException {
        String sql = "SELECT * FROM convidados WHERE reserva_id = ? ORDER BY nome";
        List<Convidado> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reservaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Busca um convidado pelo ID.
     */
    public Optional<Convidado> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM convidados WHERE id = ?";

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
     * Atualiza os dados de um convidado.
     */
    public void atualizar(Convidado convidado) throws SQLException {
        String sql = "UPDATE convidados SET nome = ?, cpf = ?, rg = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, convidado.getNome());
            ps.setString(2, convidado.getCpf());
            ps.setString(3, convidado.getRg());
            ps.setInt(4, convidado.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Remove um convidado pelo ID.
     */
    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM convidados WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Conta o número de convidados de uma reserva.
     */
    public int contarPorReserva(int reservaId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM convidados WHERE reserva_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reservaId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // ======================== Método auxiliar ========================

    private Convidado mapear(ResultSet rs) throws SQLException {
        Convidado c = new Convidado();
        c.setId(rs.getInt("id"));
        c.setReservaId(rs.getInt("reserva_id"));
        c.setNome(rs.getString("nome"));
        c.setCpf(rs.getString("cpf"));
        c.setRg(rs.getString("rg"));
        return c;
    }
}
