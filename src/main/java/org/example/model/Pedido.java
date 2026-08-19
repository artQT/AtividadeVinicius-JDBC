package org.example.model;

import jdk.jshell.Snippet;
import org.example.enums.StatusPedido;

import java.time.LocalDate;

public class Pedido {

    private int id;
    private Cliente cliente;
    private LocalDate dataPedido;
    private double volumeM3;
    private double pesoKG;
    private StatusPedido status;
    
    public Pedido(int id, Cliente cliente, LocalDate dataPedido , double volumeM3, double pesoKG, StatusPedido status) {
        this.id = id;
        this.cliente = cliente;
        this.dataPedido = LocalDate.now();
        this.volumeM3 = volumeM3;
        this.pesoKG = pesoKG;
        status = StatusPedido.PENDENTE;
    }

    public Pedido(Cliente cliente, double volumeM3, double pesoKG) {
        this.cliente = cliente;
        this.dataPedido = LocalDate.now();
        this.volumeM3 = volumeM3;
        this.pesoKG = pesoKG;
        status = StatusPedido.PENDENTE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public double getVolumeM3() {
        return volumeM3;
    }

    public void setVolumeM3(double volumeM3) {
        this.volumeM3 = volumeM3;
    }

    public double getPesoKG() {
        return pesoKG;
    }

    public void setPesoKG(double pesoKG) {
        this.pesoKG = pesoKG;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    
}
