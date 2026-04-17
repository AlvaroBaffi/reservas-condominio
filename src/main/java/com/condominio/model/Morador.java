package com.condominio.model;

/**
 * Representa um morador do condomínio.
 */
public class Morador {

    private int id;
    private String nome;
    private String cpf;
    private String rg;
    private String numeroApartamento;

    public Morador() {
    }

    public Morador(int id, String nome, String cpf, String rg, String numeroApartamento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.numeroApartamento = numeroApartamento;
    }

    public Morador(String nome, String cpf, String rg, String numeroApartamento) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNumeroApartamento() {
        return numeroApartamento;
    }

    public void setNumeroApartamento(String numeroApartamento) {
        this.numeroApartamento = numeroApartamento;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | CPF: %s | RG: %s | Apartamento: %s",
                id, nome, cpf, rg, numeroApartamento);
    }
}
