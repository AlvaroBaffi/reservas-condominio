package com.condominio.controller;

import com.condominio.model.Reserva;
import com.condominio.service.ReservaService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Controller responsável pela interface CLI de gerenciamento de Reservas.
 */
public class ReservaController {

    private final ReservaService service;
    private final Scanner scanner;

    public ReservaController(Scanner scanner) {
        this.service = new ReservaService();
        this.scanner = scanner;
    }

    /**
     * Exibe o submenu de gerenciamento de reservas.
     */
    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 6) {
            System.out.println("\n========== GERENCIAR RESERVAS ==========");
            System.out.println("1 - Registrar reserva");
            System.out.println("2 - Listar reservas");
            System.out.println("3 - Buscar reservas por data");
            System.out.println("4 - Buscar reservas por área comum");
            System.out.println("5 - Cancelar reserva");
            System.out.println("6 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> registrar();
                case 2 -> listar();
                case 3 -> buscarPorData();
                case 4 -> buscarPorArea();
                case 5 -> cancelar();
                case 6 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    /**
     * Registra uma nova reserva com validações de negócio.
     */
    private void registrar() {
        try {
            System.out.println("\n--- Registrar Reserva ---");

            System.out.print("ID do morador: ");
            int moradorId = lerInteiro();
            if (moradorId <= 0) {
                System.out.println("ID do morador inválido.");
                return;
            }

            System.out.print("ID da área comum: ");
            int areaId = lerInteiro();
            if (areaId <= 0) {
                System.out.println("ID da área comum inválido.");
                return;
            }

            System.out.print("Data da reserva (AAAA-MM-DD): ");
            LocalDate data = lerData();
            if (data == null) {
                System.out.println("Data inválida. Use o formato AAAA-MM-DD.");
                return;
            }

            System.out.print("Horário da reserva (HH:MM): ");
            LocalTime horario = lerHorario();
            if (horario == null) {
                System.out.println("Horário inválido. Use o formato HH:MM.");
                return;
            }

            Reserva reserva = new Reserva(moradorId, areaId, data, horario);
            reserva = service.registrar(reserva);
            System.out.println("Reserva registrada com sucesso! ID: " + reserva.getId());

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro de banco de dados: " + e.getMessage());
        }
    }

    /**
     * Lista todas as reservas cadastradas.
     */
    private void listar() {
        try {
            List<Reserva> reservas = service.listarTodas();
            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva cadastrada.");
            } else {
                System.out.println("\n--- Lista de Reservas ---");
                reservas.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar reservas: " + e.getMessage());
        }
    }

    /**
     * Busca reservas por uma data específica.
     */
    private void buscarPorData() {
        try {
            System.out.print("Informe a data (AAAA-MM-DD): ");
            LocalDate data = lerData();
            if (data == null) {
                System.out.println("Data inválida.");
                return;
            }

            List<Reserva> reservas = service.buscarPorData(data);
            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva encontrada para esta data.");
            } else {
                System.out.println("\n--- Reservas em " + data + " ---");
                reservas.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Busca reservas por área comum.
     */
    private void buscarPorArea() {
        try {
            System.out.print("Informe o ID da área comum: ");
            int areaId = lerInteiro();
            if (areaId <= 0) {
                System.out.println("ID inválido.");
                return;
            }

            List<Reserva> reservas = service.buscarPorAreaComum(areaId);
            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva encontrada para esta área comum.");
            } else {
                System.out.println("\n--- Reservas da Área Comum (ID: " + areaId + ") ---");
                reservas.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Cancela (remove) uma reserva pelo ID.
     */
    private void cancelar() {
        try {
            System.out.print("Informe o ID da reserva a cancelar: ");
            int id = lerInteiro();

            service.cancelar(id);
            System.out.println("Reserva cancelada com sucesso!");
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // ======================== Métodos auxiliares ========================

    private int lerInteiro() {
        try {
            String entrada = scanner.nextLine().trim();
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private LocalDate lerData() {
        try {
            String entrada = scanner.nextLine().trim();
            return LocalDate.parse(entrada);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private LocalTime lerHorario() {
        try {
            String entrada = scanner.nextLine().trim();
            return LocalTime.parse(entrada);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
