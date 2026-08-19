package org.example.model;

import org.example.enums.StatusEntrega;

import java.time.LocalDate;

public class Entrega {
    
    private int id = 0;
    private int pedido;
    private int motorista;
    private LocalDate dataSaida;
    private String dataEntrega;
    private StatusEntrega status;
    
    public Entrega(int id, int pedido, int motorista, String dataEntrega) {
        this.id = id;
        this.pedido = pedido;
        this.motorista = motorista;
        this.dataSaida = LocalDate.now();
        this.dataEntrega = dataEntrega;
        status = StatusEntrega.EM_ROTA;
    }

    public Entrega(int pedido, int motorista, String dataEntrega) {
        this.pedido = pedido;
        this.motorista = motorista;
        this.dataSaida = LocalDate.now();
        this.dataEntrega = dataEntrega;
        status = StatusEntrega.EM_ROTA;
    }

    public Entrega() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public int getMotorista() {
        return motorista;
    }

    public void setMotorista(int motorista) {
        this.motorista = motorista;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public StatusEntrega getStatus() {
        return status;
    }

    public void setStatus(StatusEntrega status) {
        this.status = status;
    }
}
