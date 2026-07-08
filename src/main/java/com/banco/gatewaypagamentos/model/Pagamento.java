package com.banco.gatewaypagamentos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contaOrigemId;
    private Long contaDestinoId;
    private double valor;
    private String status;
    private String metodo;
    private String descricao;
    private LocalDateTime createdt;

    public Pagamento() {}

    public Pagamento(Long contaOrigemId, Long contaDestinoId, double valor, String status, String metodo, String descricao) {
        this.contaOrigemId = contaOrigemId;
        this.contaDestinoId = contaDestinoId;
        this.valor = valor;
        this.status = status;
        this.metodo = metodo;
        this.descricao = descricao;
        this.createdt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getContaOrigemId() { return contaOrigemId; }
    public Long getContaDestinoId() { return contaDestinoId; }
    public double getValor() { return valor; }
    public String getStatus() { return status; }
    public String getMetodo() { return metodo; }
    public String getDescricao() { return descricao; }
    public LocalDateTime getDataHora() { return createdt; }

    public void setStatus(String status) { this.status = status; }
}
