package com.condominio;

import com.condominio.config.DatabaseConnection;
import com.condominio.controller.AreaComumController;
import com.condominio.controller.CondominioController;
import com.condominio.controller.ConvidadoController;
import com.condominio.controller.MoradorController;
import com.condominio.controller.ReservaController;

import java.util.Scanner;

/**
 * Classe principal do sistema de gerenciamento de reservas de áreas comuns.
 * Exibe o menu principal e direciona para os submenus de cada entidade.
 *
 * Execução: mvn compile exec:java
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=====================================================");
        System.out.println("  SISTEMA DE RESERVAS DE ÁREAS COMUNS - CONDOMÍNIO");
        System.out.println("=====================================================");

        // Testa conexão com o banco de dados
        try {
            DatabaseConnection.getConnection();
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!\n");
        } catch (Exception e) {
            System.out.println("ERRO: Não foi possível conectar ao banco de dados.");
            System.out.println("Verifique se o MySQL está ativo e se o banco 'condominio_db' foi criado.");
            System.out.println("Detalhes: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        // Inicializa os controllers
        CondominioController condominioCtrl = new CondominioController(scanner);
        MoradorController moradorCtrl = new MoradorController(scanner);
        AreaComumController areaComumCtrl = new AreaComumController(scanner);
        ReservaController reservaCtrl = new ReservaController(scanner);
        ConvidadoController convidadoCtrl = new ConvidadoController(scanner);

        int opcao = -1;
        while (opcao != 6) {
            System.out.println("\n==================== MENU PRINCIPAL ====================");
            System.out.println("1 - Gerenciar Condomínio");
            System.out.println("2 - Gerenciar Moradores");
            System.out.println("3 - Gerenciar Áreas Comuns");
            System.out.println("4 - Gerenciar Reservas");
            System.out.println("5 - Gerenciar Convidados");
            System.out.println("6 - Sair");
            System.out.println("========================================================");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1 -> condominioCtrl.exibirMenu();
                case 2 -> moradorCtrl.exibirMenu();
                case 3 -> areaComumCtrl.exibirMenu();
                case 4 -> reservaCtrl.exibirMenu();
                case 5 -> convidadoCtrl.exibirMenu();
                case 6 -> {
                    System.out.println("\nEncerrando o sistema...");
                    DatabaseConnection.closeConnection();
                    System.out.println("Conexão encerrada. Até logo!");
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }
}
