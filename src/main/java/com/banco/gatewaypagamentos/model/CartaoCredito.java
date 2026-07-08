package com.banco.gatewaypagamentos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CartaoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contaId;
    private String numeroOculto;
    private String bandeira;
    private String titular;
    private String validade;
    private String cvv;
    private double limite;
    private double utilizado;
    private String status;
    private LocalDateTime createdt;

    public CartaoCredito() {}

    public CartaoCredito(Long contaId, String numeroOculto, String bandeira, String titular,
                         String validade, String cvv, double limite, String status) {
        this.contaId = contaId;
        this.numeroOculto = numeroOculto;
        this.bandeira = bandeira;
        this.titular = titular;
        this.validade = validade;
        this.cvv = cvv;
        this.limite = limite;
        this.utilizado = 0.0;
        this.status = status;
        this.createdt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getContaId() { return contaId; }
    public String getNumeroOculto() { return numeroOculto; }
    public String getBandeira() { return bandeira; }
    public String getTitular() { return titular; }
    public String getValidade() { return validade; }
    public String getCvv() { return cvv; }
    public double getLimite() { return limite; }
    public double getUtilizado() { return utilizado; }
    public String getStatus() { return status; }
    public LocalDateTime getDataHora() { return createdt; }

    public void setStatus(String status) { this.status = status; }
    public void setUtilizado(double utilizado) { this.utilizado = utilizado; }
    public void setNumeroOculto(String numeroOculto) { this.numeroOculto = numeroOculto; }
    public void setValidade(String validade) { this.validade = validade; }
    public void setCvv(String cvv) { this.cvv = cvv; }
}
