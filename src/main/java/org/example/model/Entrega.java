package org.example.model;

import org.example.enums.StatusEntrega;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Entrega {
    
    private int id = 0;
    private Pedido pedido;
    private Motorista motorista;
    private LocalDateTime dataSaida;
    private String dataEntrega;
    private StatusEntrega status;
    
    public Entrega(int id, Pedido pedido, Motorista motorista, LocalDateTime dataSaida, String dataEntrega, StatusEntrega statusEntrega) {
        this.id = id;
        this.pedido = pedido;
        this.motorista = motorista;
        this.dataSaida = LocalDateTime.now();
        this.dataEntrega = dataEntrega;
        this.status = statusEntrega;
    }

    public Entrega(Pedido pedido, Motorista motorista, String dataEntrega, StatusEntrega statusEntrega) {
        this.pedido = pedido;
        this.motorista = motorista;
        this.dataSaida = LocalDateTime.now();
        this.dataEntrega = dataEntrega;
        this.status = statusEntrega;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
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
