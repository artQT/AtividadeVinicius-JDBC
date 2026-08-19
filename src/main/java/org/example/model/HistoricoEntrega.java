package org.example.model;

import java.time.LocalDate;

public class HistoricoEntrega {

    private int id;
    private Entrega entrega;
    private LocalDate dataEvento;
    private String descricao;
    
    public HistoricoEntrega(int id, Entrega entrega, LocalDate dataEvento, String descricao) {
        this.id = id;
        this.entrega = entrega;
        this.dataEvento = dataEvento;
        this.descricao = descricao;
    }

    public HistoricoEntrega(Entrega entrega, LocalDate dataEvento, String descricao) {
        this.entrega = entrega;
        this.dataEvento = dataEvento;
        this.descricao = descricao;
    }

    public HistoricoEntrega() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
