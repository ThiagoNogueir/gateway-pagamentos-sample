package com.banco.gatewaypagamentos.dto;

import java.util.List;

public record ExtratoResponse(
        String numeroConta,
        String titular,
        double saldoAtual,
        List<ExtratoItem> movimentacoes
) {
    public record ExtratoItem(
            String id,
            String tipo,
            String descricao,
            double valor,
            String dataHora,
            String status
    ) {}
}
