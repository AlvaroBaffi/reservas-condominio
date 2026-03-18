package com.condominio.repository;

import com.condominio.config.DatabaseConnection;
import com.condominio.model.Condominio;

import java.sql.*;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Repositório responsável pelas operações de banco de dados da entidade Condominio.
 */
public class CondominioRepository {

    /**
     * Insere um novo registro de condomínio no banco.
     */
    public Condominio criar(Condominio cond) throws SQLException {
        String sql = """
                INSERT INTO condominio
                (dom, seg, ter, qua, qui, sex, sab,
                 horario_dom, horario_seg, horario_ter, horario_qua,
                 horario_qui, horario_sex, horario_sab)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preencherStatement(ps, cond);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cond.setId(rs.getInt(1));
                }
            }
        }
        return cond;
    }

    /**
     * Atualiza as regras de dias e horários do condomínio.
     */
    public void atualizar(Condominio cond) throws SQLException {
        String sql = """
                UPDATE condominio SET
                    dom = ?, seg = ?, ter = ?, qua = ?, qui = ?, sex = ?, sab = ?,
                    horario_dom = ?, horario_seg = ?, horario_ter = ?,
                    horario_qua = ?, horario_qui = ?, horario_sex = ?, horario_sab = ?
                WHERE id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            preencherStatement(ps, cond);
            ps.setInt(15, cond.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Busca o primeiro registro de condomínio (cada instalação possui apenas um).
     */
    public Optional<Condominio> buscar() throws SQLException {
        String sql = "SELECT * FROM condominio LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return Optional.of(mapear(rs));
            }
        }
        return Optional.empty();
    }

    /**
     * Busca o condomínio por ID.
     */
    public Optional<Condominio> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM condominio WHERE id = ?";

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

    // ======================== Métodos auxiliares ========================

    /**
     * Preenche o PreparedStatement com os campos do condomínio.
     */
    private void preencherStatement(PreparedStatement ps, Condominio cond) throws SQLException {
        ps.setBoolean(1, cond.isDom());
        ps.setBoolean(2, cond.isSeg());
        ps.setBoolean(3, cond.isTer());
        ps.setBoolean(4, cond.isQua());
        ps.setBoolean(5, cond.isQui());
        ps.setBoolean(6, cond.isSex());
        ps.setBoolean(7, cond.isSab());
        ps.setTime(8, toSqlTime(cond.getHorarioDom()));
        ps.setTime(9, toSqlTime(cond.getHorarioSeg()));
        ps.setTime(10, toSqlTime(cond.getHorarioTer()));
        ps.setTime(11, toSqlTime(cond.getHorarioQua()));
        ps.setTime(12, toSqlTime(cond.getHorarioQui()));
        ps.setTime(13, toSqlTime(cond.getHorarioSex()));
        ps.setTime(14, toSqlTime(cond.getHorarioSab()));
    }

    /**
     * Mapeia um ResultSet para um objeto Condominio.
     */
    private Condominio mapear(ResultSet rs) throws SQLException {
        Condominio c = new Condominio();
        c.setId(rs.getInt("id"));
        c.setDom(rs.getBoolean("dom"));
        c.setSeg(rs.getBoolean("seg"));
        c.setTer(rs.getBoolean("ter"));
        c.setQua(rs.getBoolean("qua"));
        c.setQui(rs.getBoolean("qui"));
        c.setSex(rs.getBoolean("sex"));
        c.setSab(rs.getBoolean("sab"));
        c.setHorarioDom(toLocalTime(rs.getTime("horario_dom")));
        c.setHorarioSeg(toLocalTime(rs.getTime("horario_seg")));
        c.setHorarioTer(toLocalTime(rs.getTime("horario_ter")));
        c.setHorarioQua(toLocalTime(rs.getTime("horario_qua")));
        c.setHorarioQui(toLocalTime(rs.getTime("horario_qui")));
        c.setHorarioSex(toLocalTime(rs.getTime("horario_sex")));
        c.setHorarioSab(toLocalTime(rs.getTime("horario_sab")));
        return c;
    }

    private Time toSqlTime(LocalTime lt) {
        return lt != null ? Time.valueOf(lt) : null;
    }

    private LocalTime toLocalTime(Time t) {
        return t != null ? t.toLocalTime() : null;
    }
}
