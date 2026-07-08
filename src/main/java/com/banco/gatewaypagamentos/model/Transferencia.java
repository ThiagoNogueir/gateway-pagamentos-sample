package com.banco.gatewaypagamentos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contaOrigem;
    private String contaDestino;
    private double valor;
    private String status;
    private LocalDateTime dataHora;

    public Transferencia() {
    }

    public Transferencia(String contaOrigem, String contaDestino, double valor, String status) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.status = status;
        this.dataHora = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}