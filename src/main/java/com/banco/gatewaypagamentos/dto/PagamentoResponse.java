package com.banco.gatewaypagamentos.dto;

public record PagamentoResponse(
        Long id,
        Long contaOrigemId,
        Long contaDestinoId,
        double valor,
        String status,
        String metodo,
        String descricao,
        String createdt
) {}
