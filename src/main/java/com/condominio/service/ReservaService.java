package com.condominio.service;

import com.condominio.model.Condominio;
import com.condominio.model.Reserva;
import com.condominio.repository.AreaComumRepository;
import com.condominio.repository.MoradorRepository;
import com.condominio.repository.ReservaRepository;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas regras de negócio da entidade Reserva.
 * Aplica todas as validações de dias bloqueados, horários e disponibilidade.
 */
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final MoradorRepository moradorRepository;
    private final AreaComumRepository areaComumRepository;
    private final CondominioService condominioService;

    public ReservaService() {
        this.reservaRepository = new ReservaRepository();
        this.moradorRepository = new MoradorRepository();
        this.areaComumRepository = new AreaComumRepository();
        this.condominioService = new CondominioService();
    }

    /**
     * Registra uma nova reserva após validar todas as regras de negócio.
     *
     * Regras verificadas:
     * 1. Morador e área comum devem existir
     * 2. O dia da semana deve ser permitido
     * 3. O horário deve respeitar o bloqueio
     * 4. Não pode haver reserva duplicada (mesma área, data e horário)
     */
    public Reserva registrar(Reserva reserva) throws SQLException {
        // Validar se morador existe
        if (moradorRepository.buscarPorId(reserva.getMoradorId()).isEmpty()) {
            throw new IllegalArgumentException("Morador com ID " + reserva.getMoradorId() + " não encontrado.");
        }

        // Validar se área comum existe
        if (areaComumRepository.buscarPorId(reserva.getAreaComumId()).isEmpty()) {
            throw new IllegalArgumentException("Área comum com ID " + reserva.getAreaComumId() + " não encontrada.");
        }

        // Buscar regras do condomínio
        Optional<Condominio> condOpt = condominioService.buscar();
        if (condOpt.isPresent()) {
            Condominio cond = condOpt.get();
            validarDiaSemana(cond, reserva.getDataReserva());
            validarHorario(cond, reserva.getDataReserva(), reserva.getHorarioReserva());
        }

        // Validar disponibilidade (Regra 1)
        if (reservaRepository.existeReserva(reserva.getAreaComumId(), reserva.getDataReserva(), reserva.getHorarioReserva())) {
            throw new IllegalStateException(
                    "Já existe uma reserva para esta área comum nesta data e horário."
            );
        }

        return reservaRepository.criar(reserva);
    }

    /**
     * Lista todas as reservas.
     */
    public List<Reserva> listarTodas() throws SQLException {
        return reservaRepository.listarTodas();
    }

    /**
     * Busca reservas por data.
     */
    public List<Reserva> buscarPorData(LocalDate data) throws SQLException {
        return reservaRepository.buscarPorData(data);
    }

    /**
     * Busca reservas por área comum.
     */
    public List<Reserva> buscarPorAreaComum(int areaComumId) throws SQLException {
        return reservaRepository.buscarPorAreaComum(areaComumId);
    }

    /**
     * Cancela uma reserva pelo ID.
     */
    public boolean cancelar(int id) throws SQLException {
        Optional<Reserva> existente = reservaRepository.buscarPorId(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Reserva com ID " + id + " não encontrada.");
        }
        return reservaRepository.cancelar(id);
    }

    // ======================== Validações de regras de negócio ========================

    /**
     * Regra 2 — Verifica se o dia da semana é bloqueado.
     */
    private void validarDiaSemana(Condominio cond, LocalDate data) {
        DayOfWeek dia = data.getDayOfWeek();
        boolean bloqueado = switch (dia) {
            case SUNDAY    -> cond.isDom();
            case MONDAY    -> cond.isSeg();
            case TUESDAY   -> cond.isTer();
            case WEDNESDAY -> cond.isQua();
            case THURSDAY  -> cond.isQui();
            case FRIDAY    -> cond.isSex();
            case SATURDAY  -> cond.isSab();
        };

        if (bloqueado) {
            String nomeDia = traduzirDiaSemana(dia);
            throw new IllegalStateException(
                    "Reservas não são permitidas às " + nomeDia + "s. Este dia está bloqueado pelo condomínio."
            );
        }
    }

    /**
     * Regra 3 — Verifica se o horário respeita o bloqueio do dia.
     */
    private void validarHorario(Condominio cond, LocalDate data, LocalTime horario) {
        DayOfWeek dia = data.getDayOfWeek();
        LocalTime horarioBloqueio = switch (dia) {
            case SUNDAY    -> cond.getHorarioDom();
            case MONDAY    -> cond.getHorarioSeg();
            case TUESDAY   -> cond.getHorarioTer();
            case WEDNESDAY -> cond.getHorarioQua();
            case THURSDAY  -> cond.getHorarioQui();
            case FRIDAY    -> cond.getHorarioSex();
            case SATURDAY  -> cond.getHorarioSab();
        };

        if (horarioBloqueio != null && horario.isBefore(horarioBloqueio)) {
            String nomeDia = traduzirDiaSemana(dia);
            throw new IllegalStateException(
                    "Reservas às " + nomeDia + "s só são permitidas a partir das " + horarioBloqueio +
                    ". O horário informado (" + horario + ") é anterior ao permitido."
            );
        }
    }

    /**
     * Traduz o DayOfWeek para nome em português.
     */
    private String traduzirDiaSemana(DayOfWeek dia) {
        return switch (dia) {
            case SUNDAY    -> "Domingo";
            case MONDAY    -> "Segunda-feira";
            case TUESDAY   -> "Terça-feira";
            case WEDNESDAY -> "Quarta-feira";
            case THURSDAY  -> "Quinta-feira";
            case FRIDAY    -> "Sexta-feira";
            case SATURDAY  -> "Sábado";
        };
    }
}
