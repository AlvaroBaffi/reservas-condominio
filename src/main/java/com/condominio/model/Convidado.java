package com.condominio.model;

/**
 * Representa um convidado vinculado a uma reserva.
 */
public class Convidado {

    private int id;
    private int reservaId;
    private String nome;
    private String cpf;
    private String rg;

    public Convidado() {
    }

    public Convidado(int reservaId, String nome, String cpf, String rg) {
        this.reservaId = reservaId;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
    }

    // ======================== Getters e Setters ========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
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

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | CPF: %s | RG: %s | Reserva ID: %d",
                id, nome, cpf, rg, reservaId);
    }
}
