package com.condominio.service;

import com.condominio.model.AreaComum;
import com.condominio.model.Convidado;
import com.condominio.model.Reserva;
import com.condominio.repository.ConvidadoRepository;
import com.condominio.repository.ReservaRepository;
import com.condominio.repository.AreaComumRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas regras de negócio da entidade Convidado.
 */
public class ConvidadoService {

    private final ConvidadoRepository repository;
    private final ReservaRepository reservaRepository;
    private final AreaComumRepository areaComumRepository;

    public ConvidadoService() {
        this.repository = new ConvidadoRepository();
        this.reservaRepository = new ReservaRepository();
        this.areaComumRepository = new AreaComumRepository();
    }

    /**
     * Cadastra um novo convidado vinculado a uma reserva.
     * Verifica se a reserva existe e se a lotação máxima não foi atingida.
     */
    public Convidado cadastrar(Convidado convidado) throws SQLException {
        validarCampos(convidado);

        Optional<Reserva> reservaOpt = reservaRepository.buscarPorId(convidado.getReservaId());
        if (reservaOpt.isEmpty()) {
            throw new IllegalArgumentException("Reserva com ID " + convidado.getReservaId() + " não encontrada.");
        }

        Reserva reserva = reservaOpt.get();
        Optional<AreaComum> areaOpt = areaComumRepository.buscarPorId(reserva.getAreaComumId());
        if (areaOpt.isPresent()) {
            AreaComum area = areaOpt.get();
            if (area.getLotacaoMaxima() > 0) {
                int totalConvidados = repository.contarPorReserva(convidado.getReservaId());
                if (totalConvidados >= area.getLotacaoMaxima()) {
                    throw new IllegalStateException(
                            "Lotação máxima atingida para esta área (" + area.getLotacaoMaxima() + " convidados)."
                    );
                }
            }
        }

        return repository.criar(convidado);
    }

    /**
     * Lista todos os convidados de uma reserva.
     */
    public List<Convidado> listarPorReserva(int reservaId) throws SQLException {
        return repository.listarPorReserva(reservaId);
    }

    /**
     * Busca um convidado por ID.
     */
    public Optional<Convidado> buscarPorId(int id) throws SQLException {
        return repository.buscarPorId(id);
    }

    /**
     * Atualiza os dados de um convidado existente.
     */
    public void atualizar(Convidado convidado) throws SQLException {
        Optional<Convidado> existente = repository.buscarPorId(convidado.getId());
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Convidado com ID " + convidado.getId() + " não encontrado.");
        }
        validarCampos(convidado);
        repository.atualizar(convidado);
    }

    /**
     * Remove um convidado pelo ID.
     */
    public boolean remover(int id) throws SQLException {
        Optional<Convidado> existente = repository.buscarPorId(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Convidado com ID " + id + " não encontrado.");
        }
        return repository.remover(id);
    }

    /**
     * Valida campos obrigatórios do convidado.
     */
    private void validarCampos(Convidado convidado) {
        if (convidado.getNome() == null || convidado.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do convidado é obrigatório.");
        }
        if (convidado.getCpf() == null || convidado.getCpf().isBlank()) {
            throw new IllegalArgumentException("O CPF do convidado é obrigatório.");
        }
        if (convidado.getRg() == null || convidado.getRg().isBlank()) {
            throw new IllegalArgumentException("O RG do convidado é obrigatório.");
        }
    }
}
