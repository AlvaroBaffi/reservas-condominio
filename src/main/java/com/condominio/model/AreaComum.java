package com.condominio.model;

/**
 * Representa uma área comum do condomínio disponível para reserva.
 * Exemplos: Churrasqueira, Salão de Festas, Quadra, Piscina.
 */
public class AreaComum {

    private int id;
    private String nome;

    public AreaComum() {
    }

    public AreaComum(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public AreaComum(String nome) {

        this.nome = nome;
    }

    // ======================== Getters e Setters ========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s", id, nome);
    }
}
