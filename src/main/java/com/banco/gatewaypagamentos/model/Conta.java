package com.banco.gatewaypagamentos.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String titular;
    private String banco;
    private String agencia;
    private String tipo;
    private double saldo;
    private String status;
    private Long clienteId;
    private LocalDateTime createdt;

    public Conta() {}

    public Conta(String numero, String titular, String banco, String agencia, String tipo, double saldo, String status, Long clienteId) {
        this.numero = numero;
        this.titular = titular;
        this.banco = banco;
        this.agencia = agencia;
        this.tipo = tipo;
        this.saldo = saldo;
        this.status = status;
        this.clienteId = clienteId;
        this.createdt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getNumero() { return numero; }
    public String getTitular() { return titular; }
    public String getBanco() { return banco; }
    public String getAgencia() { return agencia; }
    public String getTipo() { return tipo; }
    public double getSaldo() { return saldo; }
    public String getStatus() { return status; }
    public Long getClienteId() { return clienteId; }
    public LocalDateTime getDataHora() { return createdt; }

    public void setNumero(String numero) { this.numero = numero; }
    public void setTitular(String titular) { this.titular = titular; }
    public void setBanco(String banco) { this.banco = banco; }
    public void setAgencia(String agencia) { this.agencia = agencia; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public void setStatus(String status) { this.status = status; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}

