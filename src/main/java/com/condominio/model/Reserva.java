package com.condominio.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa uma reserva feita por um morador para uma área comum.
 */
public class Reserva {

    private int id;
    private int moradorId;
    private int areaComumId;
    private LocalDate dataReserva;
    private LocalTime horarioReserva;

    // Campos auxiliares para exibição (preenchidos via JOIN)
    private String moradorNome;
    private String areaComumNome;

    public Reserva() {
    }

    public Reserva(int moradorId, int areaComumId, LocalDate dataReserva, LocalTime horarioReserva) {
        this.moradorId = moradorId;
        this.areaComumId = areaComumId;
        this.dataReserva = dataReserva;
        this.horarioReserva = horarioReserva;
    }

    // ======================== Getters e Setters ========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoradorId() {
        return moradorId;
    }

    public void setMoradorId(int moradorId) {
        this.moradorId = moradorId;
    }

    public int getAreaComumId() {
        return areaComumId;
    }

    public void setAreaComumId(int areaComumId) {
        this.areaComumId = areaComumId;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public LocalTime getHorarioReserva() {
        return horarioReserva;
    }

    public void setHorarioReserva(LocalTime horarioReserva) {
        this.horarioReserva = horarioReserva;
    }

    public String getMoradorNome() {
        return moradorNome;
    }

    public void setMoradorNome(String moradorNome) {
        this.moradorNome = moradorNome;
    }

    public String getAreaComumNome() {
        return areaComumNome;
    }

    public void setAreaComumNome(String areaComumNome) {
        this.areaComumNome = areaComumNome;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Morador: %s | Área: %s | Data: %s | Horário: %s",
                id,
                moradorNome != null ? moradorNome : String.valueOf(moradorId),
                areaComumNome != null ? areaComumNome : String.valueOf(areaComumId),
                dataReserva,
                horarioReserva
        );
    }
}
