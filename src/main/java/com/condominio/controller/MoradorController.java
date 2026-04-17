package com.condominio.controller;

import com.condominio.model.Morador;
import com.condominio.service.MoradorService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controller responsável pela interface CLI de gerenciamento de Moradores.
 */
public class MoradorController {

    private final MoradorService service;
    private final Scanner scanner;

    public MoradorController(Scanner scanner) {
        this.service = new MoradorService();
        this.scanner = scanner;
    }

    /**
     * Exibe o submenu de gerenciamento de moradores.
     */
    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 6) {
            System.out.println("\n========== GERENCIAR MORADORES ==========");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Remover");
            System.out.println("6 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> buscarPorId();
                case 4 -> atualizar();
                case 5 -> remover();
                case 6 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    /**
     * Cadastra um novo morador.
     */
    private void cadastrar() {
        try {
            System.out.println("\n--- Cadastrar Morador ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine().trim();
            System.out.print("RG: ");
            String rg = scanner.nextLine().trim();
            System.out.print("Número do apartamento: ");
            String apartamento = scanner.nextLine().trim();

            Morador morador = new Morador(nome, cpf, rg, apartamento);
            morador = service.cadastrar(morador);
            System.out.println("Morador cadastrado com sucesso! ID: " + morador.getId());
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Lista todos os moradores cadastrados.
     */
    private void listar() {
        try {
            List<Morador> moradores = service.listarTodos();
            if (moradores.isEmpty()) {
                System.out.println("Nenhum morador cadastrado.");
            } else {
                System.out.println("\n--- Lista de Moradores ---");
                moradores.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar moradores: " + e.getMessage());
        }
    }

    /**
     * Busca um morador por ID.
     */
    private void buscarPorId() {
        try {
            System.out.print("Informe o ID do morador: ");
            int id = lerInteiro();

            Optional<Morador> opt = service.buscarPorId(id);
            if (opt.isEmpty()) {
                System.out.println("Morador não encontrado.");
            } else {
                System.out.println(opt.get());
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um morador.
     */
    private void atualizar() {
        try {
            System.out.print("Informe o ID do morador a atualizar: ");
            int id = lerInteiro();

            Optional<Morador> opt = service.buscarPorId(id);
            if (opt.isEmpty()) {
                System.out.println("Morador não encontrado.");
                return;
            }

            Morador morador = opt.get();
            System.out.println("Dados atuais: " + morador);

            System.out.print("Novo nome (Enter para manter '" + morador.getNome() + "'): ");
            String nome = scanner.nextLine().trim();
            if (!nome.isEmpty()) {
                morador.setNome(nome);
            }

            System.out.print("Novo CPF (Enter para manter '" + morador.getCpf() + "'): ");
            String cpf = scanner.nextLine().trim();
            if (!cpf.isEmpty()) {
                morador.setCpf(cpf);
            }

            System.out.print("Novo RG (Enter para manter '" + morador.getRg() + "'): ");
            String rg = scanner.nextLine().trim();
            if (!rg.isEmpty()) {
                morador.setRg(rg);
            }

            System.out.print("Novo apartamento (Enter para manter '" + morador.getNumeroApartamento() + "'): ");
            String apto = scanner.nextLine().trim();
            if (!apto.isEmpty()) {
                morador.setNumeroApartamento(apto);
            }

            service.atualizar(morador);
            System.out.println("Morador atualizado com sucesso!");
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Remove um morador pelo ID.
     */
    private void remover() {
        try {
            System.out.print("Informe o ID do morador a remover: ");
            int id = lerInteiro();

            service.remover(id);
            System.out.println("Morador removido com sucesso!");
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
