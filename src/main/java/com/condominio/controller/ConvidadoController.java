package com.condominio.controller;

import com.condominio.model.Convidado;
import com.condominio.service.ConvidadoService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controller responsável pela interface CLI de gerenciamento de Convidados.
 */
public class ConvidadoController {

    private final ConvidadoService service;
    private final Scanner scanner;

    public ConvidadoController(Scanner scanner) {
        this.service = new ConvidadoService();
        this.scanner = scanner;
    }

    /**
     * Exibe o submenu de gerenciamento de convidados.
     */
    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 6) {
            System.out.println("\n========== GERENCIAR CONVIDADOS ==========");
            System.out.println("1 - Registrar convidado");
            System.out.println("2 - Listar convidados por reserva");
            System.out.println("3 - Buscar convidado por ID");
            System.out.println("4 - Atualizar convidado");
            System.out.println("5 - Remover convidado");
            System.out.println("6 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> registrar();
                case 2 -> listarPorReserva();
                case 3 -> buscarPorId();
                case 4 -> atualizar();
                case 5 -> remover();
                case 6 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    /**
     * Registra um novo convidado vinculado a uma reserva.
     */
    private void registrar() {
        try {
            System.out.println("\n--- Registrar Convidado ---");

            System.out.print("ID da reserva: ");
            int reservaId = lerInteiro();
            if (reservaId <= 0) {
                System.out.println("ID da reserva inválido.");
                return;
            }

            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();

            System.out.print("CPF: ");
            String cpf = scanner.nextLine().trim();

            System.out.print("RG: ");
            String rg = scanner.nextLine().trim();

            Convidado convidado = new Convidado(reservaId, nome, cpf, rg);
            convidado = service.cadastrar(convidado);
            System.out.println("Convidado registrado com sucesso! ID: " + convidado.getId());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro de banco de dados: " + e.getMessage());
        }
    }

    /**
     * Lista todos os convidados de uma reserva.
     */
    private void listarPorReserva() {
        try {
            System.out.print("Informe o ID da reserva: ");
            int reservaId = lerInteiro();
            if (reservaId <= 0) {
                System.out.println("ID inválido.");
                return;
            }

            List<Convidado> convidados = service.listarPorReserva(reservaId);
            if (convidados.isEmpty()) {
                System.out.println("Nenhum convidado registrado para esta reserva.");
            } else {
                System.out.println("\n--- Convidados da Reserva (ID: " + reservaId + ") ---");
                convidados.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Busca um convidado por ID.
     */
    private void buscarPorId() {
        try {
            System.out.print("Informe o ID do convidado: ");
            int id = lerInteiro();

            Optional<Convidado> opt = service.buscarPorId(id);
            if (opt.isEmpty()) {
                System.out.println("Convidado não encontrado.");
            } else {
                System.out.println(opt.get());
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um convidado.
     */
    private void atualizar() {
        try {
            System.out.print("Informe o ID do convidado a atualizar: ");
            int id = lerInteiro();

            Optional<Convidado> opt = service.buscarPorId(id);
            if (opt.isEmpty()) {
                System.out.println("Convidado não encontrado.");
                return;
            }

            Convidado convidado = opt.get();
            System.out.println("Dados atuais: " + convidado);

            System.out.print("Novo nome (Enter para manter '" + convidado.getNome() + "'): ");
            String nome = scanner.nextLine().trim();
            if (!nome.isEmpty()) {
                convidado.setNome(nome);
            }

            System.out.print("Novo CPF (Enter para manter '" + convidado.getCpf() + "'): ");
            String cpf = scanner.nextLine().trim();
            if (!cpf.isEmpty()) {
                convidado.setCpf(cpf);
            }

            System.out.print("Novo RG (Enter para manter '" + convidado.getRg() + "'): ");
            String rg = scanner.nextLine().trim();
            if (!rg.isEmpty()) {
                convidado.setRg(rg);
            }

            service.atualizar(convidado);
            System.out.println("Convidado atualizado com sucesso!");
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Remove um convidado pelo ID.
     */
    private void remover() {
        try {
            System.out.print("Informe o ID do convidado a remover: ");
            int id = lerInteiro();

            service.remover(id);
            System.out.println("Convidado removido com sucesso!");
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
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
