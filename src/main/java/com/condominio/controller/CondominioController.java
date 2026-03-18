package com.condominio.controller;

import com.condominio.model.Condominio;
import com.condominio.service.CondominioService;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controller responsável pela interface CLI de gerenciamento do Condomínio.
 */
public class CondominioController {

    private final CondominioService service;
    private final Scanner scanner;

    public CondominioController(Scanner scanner) {
        this.service = new CondominioService();
        this.scanner = scanner;
    }

    /**
     * Exibe o submenu de gerenciamento do condomínio.
     */
    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 4) {
            System.out.println("\n========== GERENCIAR CONDOMÍNIO ==========");
            System.out.println("1 - Criar condomínio");
            System.out.println("2 - Atualizar regras");
            System.out.println("3 - Buscar dados do condomínio");
            System.out.println("4 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> criarCondominio();
                case 2 -> atualizarRegras();
                case 3 -> buscarCondominio();
                case 4 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    /**
     * Cria um novo registro de condomínio com as regras de bloqueio.
     */
    private void criarCondominio() {
        try {
            System.out.println("\n--- Criar Condomínio ---");
            Condominio cond = preencherCondominio(new Condominio());
            cond = service.criar(cond);
            System.out.println("Condomínio criado com sucesso! ID: " + cond.getId());
        } catch (IllegalStateException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Atualiza as regras de dias e horários do condomínio existente.
     */
    private void atualizarRegras() {
        try {
            Optional<Condominio> opt = service.buscar();
            if (opt.isEmpty()) {
                System.out.println("Nenhum condomínio cadastrado. Crie um primeiro.");
                return;
            }

            Condominio cond = opt.get();
            System.out.println("\n--- Atualizar Regras do Condomínio (ID: " + cond.getId() + ") ---");
            cond = preencherCondominio(cond);
            service.atualizar(cond);
            System.out.println("Regras atualizadas com sucesso!");
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Exibe os dados do condomínio cadastrado.
     */
    private void buscarCondominio() {
        try {
            Optional<Condominio> opt = service.buscar();
            if (opt.isEmpty()) {
                System.out.println("Nenhum condomínio cadastrado.");
            } else {
                System.out.println(opt.get());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar condomínio: " + e.getMessage());
        }
    }

    // ======================== Métodos auxiliares ========================

    /**
     * Preenche os campos de um condomínio via entrada do terminal.
     */
    private Condominio preencherCondominio(Condominio cond) {
        System.out.println("Para cada dia, informe se é BLOQUEADO para reservas (S/N):");

        cond.setDom(lerBoolean("Domingo bloqueado? (S/N): "));
        cond.setSeg(lerBoolean("Segunda bloqueada? (S/N): "));
        cond.setTer(lerBoolean("Terça bloqueada? (S/N): "));
        cond.setQua(lerBoolean("Quarta bloqueada? (S/N): "));
        cond.setQui(lerBoolean("Quinta bloqueada? (S/N): "));
        cond.setSex(lerBoolean("Sexta bloqueada? (S/N): "));
        cond.setSab(lerBoolean("Sábado bloqueado? (S/N): "));

        System.out.println("\nPara os dias NÃO bloqueados, informe o horário mínimo de reserva (HH:MM).");
        System.out.println("Deixe em branco para não ter restrição de horário.\n");

        if (!cond.isDom()) cond.setHorarioDom(lerHorarioOpcional("Horário mínimo Domingo (HH:MM): "));
        if (!cond.isSeg()) cond.setHorarioSeg(lerHorarioOpcional("Horário mínimo Segunda (HH:MM): "));
        if (!cond.isTer()) cond.setHorarioTer(lerHorarioOpcional("Horário mínimo Terça (HH:MM): "));
        if (!cond.isQua()) cond.setHorarioQua(lerHorarioOpcional("Horário mínimo Quarta (HH:MM): "));
        if (!cond.isQui()) cond.setHorarioQui(lerHorarioOpcional("Horário mínimo Quinta (HH:MM): "));
        if (!cond.isSex()) cond.setHorarioSex(lerHorarioOpcional("Horário mínimo Sexta (HH:MM): "));
        if (!cond.isSab()) cond.setHorarioSab(lerHorarioOpcional("Horário mínimo Sábado (HH:MM): "));

        return cond;
    }

    private boolean lerBoolean(String mensagem) {
        System.out.print(mensagem);
        String entrada = scanner.nextLine().trim().toUpperCase();
        return entrada.equals("S");
    }

    private LocalTime lerHorarioOpcional(String mensagem) {
        System.out.print(mensagem);
        String entrada = scanner.nextLine().trim();
        if (entrada.isEmpty()) {
            return null;
        }
        try {
            return LocalTime.parse(entrada);
        } catch (DateTimeParseException e) {
            System.out.println("Formato inválido. Use HH:MM. Valor ignorado.");
            return null;
        }
    }

    private int lerInteiro() {
        try {
            String entrada = scanner.nextLine().trim();
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
