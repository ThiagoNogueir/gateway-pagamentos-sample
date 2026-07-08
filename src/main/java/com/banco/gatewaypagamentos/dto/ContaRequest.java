package com.banco.gatewaypagamentos.dto;

public record ContaRequest(String numero, String titular, String banco, String agencia, String tipo, double saldo, String status, Long clienteId) {}
