package com.banco.gatewaypagamentos.dto;

public record ClienteResponse(Long id, String nome, String cpfCnpj, String email, String telefone, String tipo, String createdt) {}
