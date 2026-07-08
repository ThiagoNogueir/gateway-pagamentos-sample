package com.banco.gatewaypagamentos.dto;

public record TransferenciaRequest(String contaOrigem, String contaDestino, double valor) {}