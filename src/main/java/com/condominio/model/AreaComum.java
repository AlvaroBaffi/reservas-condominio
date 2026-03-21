package com.condominio.model;

/**
 * Representa uma área comum do condomínio disponível para reserva.
 * Exemplos: Churrasqueira, Salão de Festas, Quadra, Piscina.
 */
public class AreaComum {

    private int id;
    private String nome;
    private int capacidadeMaxima;

    public AreaComum() {
    }

    public AreaComum(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public AreaComum(String nome) {

        this.nome = nome;
    }
    //usuario pode escolher se a area cadastrada vai ter uma capacidade maximo. Por exemplo: Salão de festas - capacidade maxima: 400 pessoas
    public AreaComum(int capacidadeMaxima){
        this.capacidadeMaxima = capacidadeMaxima;
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

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s", id, nome);
    }
}
