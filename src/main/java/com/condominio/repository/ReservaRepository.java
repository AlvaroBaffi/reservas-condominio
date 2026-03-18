package com.condominio.repository;

import com.condominio.config.DatabaseConnection;
import com.condominio.model.Reserva;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório responsável pelas operações de banco de dados da entidade Reserva.
 */
public class ReservaRepository {

    /**
     * Insere uma nova reserva no banco.
     */
    public Reserva criar(Reserva reserva) throws SQLException {
        String sql = """
                INSERT INTO reservas (morador_id, area_comum_id, data_reserva, horario_reserva)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, reserva.getMoradorId());
            ps.setInt(2, reserva.getAreaComumId());
            ps.setDate(3, Date.valueOf(reserva.getDataReserva()));
            ps.setTime(4, Time.valueOf(reserva.getHorarioReserva()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    reserva.setId(rs.getInt(1));
                }
            }
        }
        return reserva;
    }

    /**
     * Lista todas as reservas com nomes de morador e área comum.
     */
    public List<Reserva> listarTodas() throws SQLException {
        String sql = """
                SELECT r.*, m.nome AS morador_nome, a.nome AS area_nome
                FROM reservas r
                JOIN moradores m ON r.morador_id = m.id
                JOIN areas_comuns a ON r.area_comum_id = a.id
                ORDER BY r.data_reserva, r.horario_reserva
                """;

        return executarConsultaLista(sql);
    }

    /**
     * Busca reservas por data específica.
     */
    public List<Reserva> buscarPorData(LocalDate data) throws SQLException {
        String sql = """
                SELECT r.*, m.nome AS morador_nome, a.nome AS area_nome
                FROM reservas r
                JOIN moradores m ON r.morador_id = m.id
                JOIN areas_comuns a ON r.area_comum_id = a.id
                WHERE r.data_reserva = ?
                ORDER BY r.horario_reserva
                """;
        List<Reserva> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(data));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Busca reservas por área comum.
     */
    public List<Reserva> buscarPorAreaComum(int areaComumId) throws SQLException {
        String sql = """
                SELECT r.*, m.nome AS morador_nome, a.nome AS area_nome
                FROM reservas r
                JOIN moradores m ON r.morador_id = m.id
                JOIN areas_comuns a ON r.area_comum_id = a.id
                WHERE r.area_comum_id = ?
                ORDER BY r.data_reserva, r.horario_reserva
                """;
        List<Reserva> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, areaComumId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Verifica se já existe reserva para a mesma área, data e horário.
     */
    public boolean existeReserva(int areaComumId, LocalDate data, LocalTime horario) throws SQLException {
        String sql = """
                SELECT COUNT(*) FROM reservas
                WHERE area_comum_id = ? AND data_reserva = ? AND horario_reserva = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, areaComumId);
            ps.setDate(2, Date.valueOf(data));
            ps.setTime(3, Time.valueOf(horario));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Busca uma reserva pelo ID.
     */
    public Optional<Reserva> buscarPorId(int id) throws SQLException {
        String sql = """
                SELECT r.*, m.nome AS morador_nome, a.nome AS area_nome
                FROM reservas r
                JOIN moradores m ON r.morador_id = m.id
                JOIN areas_comuns a ON r.area_comum_id = a.id
                WHERE r.id = ?
                """;

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
     * Cancela (remove) uma reserva pelo ID.
     */
    public boolean cancelar(int id) throws SQLException {
        String sql = "DELETE FROM reservas WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ======================== Métodos auxiliares ========================

    private List<Reserva> executarConsultaLista(String sql) throws SQLException {
        List<Reserva> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    private Reserva mapear(ResultSet rs) throws SQLException {
        Reserva r = new Reserva();
        r.setId(rs.getInt("id"));
        r.setMoradorId(rs.getInt("morador_id"));
        r.setAreaComumId(rs.getInt("area_comum_id"));
        r.setDataReserva(rs.getDate("data_reserva").toLocalDate());
        r.setHorarioReserva(rs.getTime("horario_reserva").toLocalTime());
        r.setMoradorNome(rs.getString("morador_nome"));
        r.setAreaComumNome(rs.getString("area_nome"));
        return r;
    }
}
