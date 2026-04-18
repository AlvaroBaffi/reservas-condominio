package com.condominio.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados MySQL.
 * Utiliza o padrão Singleton para reutilizar a mesma conexão durante a execução.
 */
public class DatabaseConnection {

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "condominio_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static final String URL = String.format(
            "jdbc:mysql://%s:%s@%s:%s/%s?useSSL=false&serverTimezone=UTC",
            USER, PASSWORD, HOST, PORT, DATABASE
    );

    private static Connection connection;

    private DatabaseConnection() {
        // Construtor privado — uso estático apenas
    }

    /**
     * Retorna a conexão ativa com o banco de dados.
     * Cria uma nova conexão caso não exista ou esteja fechada.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL não encontrado. Verifique as dependências.", e);
            }
        }
        return connection;
    }

    /**
     * Fecha a conexão com o banco de dados se estiver aberta.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
