package com.condominio.model;

/**
 * Representa um morador do condomínio.
 */
public class Morador {

    private int id;
    private String nome;
    private String numeroApartamento;

    public Morador() {
    }

    public Morador(int id, String nome, String numeroApartamento) {
        this.id = id;
        this.nome = nome;
        this.numeroApartamento = numeroApartamento;
    }

    public Morador(String nome, String numeroApartamento) {
        this.nome = nome;
        this.numeroApartamento = numeroApartamento;
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

    public String getNumeroApartamento() {
        return numeroApartamento;
    }

    public void setNumeroApartamento(String numeroApartamento) {
        this.numeroApartamento = numeroApartamento;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Apartamento: %s", id, nome, numeroApartamento);
    }
}
