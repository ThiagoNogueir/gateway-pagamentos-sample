package com.banco.gatewaypagamentos.dto;

public record ContaResponse(Long id, String numero, String titular, String banco, String agencia, String tipo, double saldo, String status, Long clienteId, String createdt) {}
