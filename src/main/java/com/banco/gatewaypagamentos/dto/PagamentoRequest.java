package com.banco.gatewaypagamentos.dto;

import java.time.LocalDate;

public record PagamentoRequest(
        Long contaOrigemId,
        Long contaDestinoId,
        double valor,
        String metodo,
        String descricao,
        String chavePix,
        String tipoChavePix,
        String codigoBarrasBoleto,
        LocalDate dataVencimentoBoleto,
        Long cartaoCreditoId,
        Integer parcelas
) {}
