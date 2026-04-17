package com.condominio.model;

/**
 * Representa uma área comum do condomínio disponível para reserva.
 * Exemplos: Churrasqueira, Salão de Festas, Quadra, Piscina.
 */
public class AreaComum {

    private int id;
    private String nome;
    private int lotacaoMaxima;

    public AreaComum() {
    }

    public AreaComum(int id, String nome, int lotacaoMaxima) {
        this.id = id;
        this.nome = nome;
        this.lotacaoMaxima = lotacaoMaxima;
    }

    public AreaComum(String nome, int lotacaoMaxima) {
        this.nome = nome;
        this.lotacaoMaxima = lotacaoMaxima;
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

    public int getLotacaoMaxima() {
        return lotacaoMaxima;
    }

    public void setLotacaoMaxima(int lotacaoMaxima) {
        this.lotacaoMaxima = lotacaoMaxima;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Lotação Máxima: %d", id, nome, lotacaoMaxima);
    }
}
