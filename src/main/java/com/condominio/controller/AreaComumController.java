package com.condominio.controller;

import com.condominio.model.AreaComum;
import com.condominio.service.AreaComumService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controller responsável pela interface CLI de gerenciamento de Áreas Comuns.
 */
public class AreaComumController {

    private final AreaComumService service;
    private final Scanner scanner;

    public AreaComumController(Scanner scanner) {
        this.service = new AreaComumService();
        this.scanner = scanner;
    }

    /**
     * Exibe o submenu de gerenciamento de áreas comuns.
     */
    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 5) {
            System.out.println("\n========== GERENCIAR ÁREAS COMUNS ==========");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("5 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> atualizar();
                case 4 -> remover();
                case 5 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    /**
     * Cadastra uma nova área comum.
     */
    private void cadastrar() {
        try {
            System.out.println("\n--- Cadastrar Área Comum ---");
            System.out.print("Nome da área: ");
            String nome = scanner.nextLine().trim();

            AreaComum area = new AreaComum(nome);
            area = service.cadastrar(area);
            System.out.println("Área comum cadastrada com sucesso! ID: " + area.getId());
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Lista todas as áreas comuns cadastradas.
     */
    private void listar() {
        try {
            List<AreaComum> areas = service.listarTodas();
            if (areas.isEmpty()) {
                System.out.println("Nenhuma área comum cadastrada.");
            } else {
                System.out.println("\n--- Lista de Áreas Comuns ---");
                areas.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar áreas comuns: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de uma área comum.
     */
    private void atualizar() {
        try {
            System.out.print("Informe o ID da área a atualizar: ");
            int id = lerInteiro();

            Optional<AreaComum> opt = service.buscarPorId(id);
            if (opt.isEmpty()) {
                System.out.println("Área comum não encontrada.");
                return;
            }

            AreaComum area = opt.get();
            System.out.println("Dados atuais: " + area);

            System.out.print("Novo nome (Enter para manter '" + area.getNome() + "'): ");
            String nome = scanner.nextLine().trim();
            if (!nome.isEmpty()) {
                area.setNome(nome);
            }

            service.atualizar(area);
            System.out.println("Área comum atualizada com sucesso!");
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Remove uma área comum pelo ID.
     */
    private void remover() {
        try {
            System.out.print("Informe o ID da área a remover: ");
            int id = lerInteiro();

            service.remover(id);
            System.out.println("Área comum removida com sucesso!");
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
