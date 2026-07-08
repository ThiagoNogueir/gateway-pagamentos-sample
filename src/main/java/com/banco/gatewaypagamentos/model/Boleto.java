package com.banco.gatewaypagamentos.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pagamentoId;
    private Long contaId;
    private String codigoBarras;
    private String linhaDigitavel;
    private String nossoNumero;
    private double valor;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private String status;
    private LocalDateTime createdt;

    public Boleto() {}

    public Boleto(Long pagamentoId, Long contaId, String codigoBarras, String linhaDigitavel,
                  String nossoNumero, double valor, LocalDate dataVencimento, String status) {
        this.pagamentoId = pagamentoId;
        this.contaId = contaId;
        this.codigoBarras = codigoBarras;
        this.linhaDigitavel = linhaDigitavel;
        this.nossoNumero = nossoNumero;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.status = status;
        this.createdt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getPagamentoId() { return pagamentoId; }
    public Long getContaId() { return contaId; }
    public String getCodigoBarras() { return codigoBarras; }
    public String getLinhaDigitavel() { return linhaDigitavel; }
    public String getNossoNumero() { return nossoNumero; }
    public double getValor() { return valor; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public String getStatus() { return status; }
    public LocalDateTime getDataHora() { return createdt; }

    public void setStatus(String status) { this.status = status; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }
}
