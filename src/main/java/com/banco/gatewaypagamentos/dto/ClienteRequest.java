package com.banco.gatewaypagamentos.dto;

public record ClienteRequest(String nome, String cpfCnpj, String email, String telefone, String tipo) {}
