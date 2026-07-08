package com.banco.gatewaypagamentos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpfCnpj;
    private String email;
    private String telefone;
    private String tipo;
    private LocalDateTime createdt;

    public Cliente() {}

    public Cliente(String nome, String cpfCnpj, String email, String telefone, String tipo) {
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.telefone = telefone;
        this.tipo = tipo;
        this.createdt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpfCnpj() { return cpfCnpj; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getTipo() { return tipo; }
    public LocalDateTime getDataHora() { return createdt; }

    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
