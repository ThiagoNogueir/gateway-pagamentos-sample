package com.banco.gatewaypagamentos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contaId;
    private String chave;
    private String tipoChave;
    private String status;
    private LocalDateTime createdt;

    public Pix() {}

    public Pix(Long contaId, String chave, String tipoChave, String status) {
        this.contaId = contaId;
        this.chave = chave;
        this.tipoChave = tipoChave;
        this.status = status;
        this.createdt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getContaId() { return contaId; }
    public String getChave() { return chave; }
    public String getTipoChave() { return tipoChave; }
    public String getStatus() { return status; }
    public LocalDateTime getDataHora() { return createdt; }

    public void setStatus(String status) { this.status = status; }
}
