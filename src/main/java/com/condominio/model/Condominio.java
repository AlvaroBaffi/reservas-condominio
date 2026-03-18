package com.condominio.model;

import java.time.LocalTime;

/**
 * Representa o condomínio e suas regras de bloqueio de dias/horários.
 * Campos booleanos true = dia BLOQUEADO para reservas.
 * Campos de horário = até que hora reservas são proibidas naquele dia.
 */
public class Condominio {

    private int id;

    // Dias bloqueados (true = bloqueado)
    private boolean dom;
    private boolean seg;
    private boolean ter;
    private boolean qua;
    private boolean qui;
    private boolean sex;
    private boolean sab;

    // Horários de bloqueio (reservas antes desse horário são proibidas)
    private LocalTime horarioDom;
    private LocalTime horarioSeg;
    private LocalTime horarioTer;
    private LocalTime horarioQua;
    private LocalTime horarioQui;
    private LocalTime horarioSex;
    private LocalTime horarioSab;

    public Condominio() {
    }

    // ======================== Getters e Setters ========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDom() {
        return dom;
    }

    public void setDom(boolean dom) {
        this.dom = dom;
    }

    public boolean isSeg() {
        return seg;
    }

    public void setSeg(boolean seg) {
        this.seg = seg;
    }

    public boolean isTer() {
        return ter;
    }

    public void setTer(boolean ter) {
        this.ter = ter;
    }

    public boolean isQua() {
        return qua;
    }

    public void setQua(boolean qua) {
        this.qua = qua;
    }

    public boolean isQui() {
        return qui;
    }

    public void setQui(boolean qui) {
        this.qui = qui;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public boolean isSab() {
        return sab;
    }

    public void setSab(boolean sab) {
        this.sab = sab;
    }

    public LocalTime getHorarioDom() {
        return horarioDom;
    }

    public void setHorarioDom(LocalTime horarioDom) {
        this.horarioDom = horarioDom;
    }

    public LocalTime getHorarioSeg() {
        return horarioSeg;
    }

    public void setHorarioSeg(LocalTime horarioSeg) {
        this.horarioSeg = horarioSeg;
    }

    public LocalTime getHorarioTer() {
        return horarioTer;
    }

    public void setHorarioTer(LocalTime horarioTer) {
        this.horarioTer = horarioTer;
    }

    public LocalTime getHorarioQua() {
        return horarioQua;
    }

    public void setHorarioQua(LocalTime horarioQua) {
        this.horarioQua = horarioQua;
    }

    public LocalTime getHorarioQui() {
        return horarioQui;
    }

    public void setHorarioQui(LocalTime horarioQui) {
        this.horarioQui = horarioQui;
    }

    public LocalTime getHorarioSex() {
        return horarioSex;
    }

    public void setHorarioSex(LocalTime horarioSex) {
        this.horarioSex = horarioSex;
    }

    public LocalTime getHorarioSab() {
        return horarioSab;
    }

    public void setHorarioSab(LocalTime horarioSab) {
        this.horarioSab = horarioSab;
    }

    @Override
    public String toString() {
        return String.format(
                """
                ========== Regras do Condomínio (ID: %d) ==========
                Dom: %s | Horário bloqueio: %s
                Seg: %s | Horário bloqueio: %s
                Ter: %s | Horário bloqueio: %s
                Qua: %s | Horário bloqueio: %s
                Qui: %s | Horário bloqueio: %s
                Sex: %s | Horário bloqueio: %s
                Sáb: %s | Horário bloqueio: %s
                ====================================================""",
                id,
                dom ? "BLOQUEADO" : "Liberado", horarioDom != null ? horarioDom : "—",
                seg ? "BLOQUEADO" : "Liberado", horarioSeg != null ? horarioSeg : "—",
                ter ? "BLOQUEADO" : "Liberado", horarioTer != null ? horarioTer : "—",
                qua ? "BLOQUEADO" : "Liberado", horarioQua != null ? horarioQua : "—",
                qui ? "BLOQUEADO" : "Liberado", horarioQui != null ? horarioQui : "—",
                sex ? "BLOQUEADO" : "Liberado", horarioSex != null ? horarioSex : "—",
                sab ? "BLOQUEADO" : "Liberado", horarioSab != null ? horarioSab : "—"
        );
    }
}
