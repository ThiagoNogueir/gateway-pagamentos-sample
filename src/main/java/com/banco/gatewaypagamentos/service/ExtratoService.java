package com.banco.gatewaypagamentos.service;

import com.banco.gatewaypagamentos.dto.ExtratoResponse;
import com.banco.gatewaypagamentos.dto.ExtratoResponse.ExtratoItem;
import com.banco.gatewaypagamentos.model.Conta;
import com.banco.gatewaypagamentos.model.Pagamento;
import com.banco.gatewaypagamentos.model.Transferencia;
import com.banco.gatewaypagamentos.repository.ContaRepository;
import com.banco.gatewaypagamentos.repository.PagamentoRepository;
import com.banco.gatewaypagamentos.repository.TransferenciaRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ExtratoService {

    private final ContaRepository contaRepository;
    private final TransferenciaRepository transferenciaRepository;
    private final PagamentoRepository pagamentoRepository;

    public ExtratoService(ContaRepository contaRepository, TransferenciaRepository transferenciaRepository,
                          PagamentoRepository pagamentoRepository) {
        this.contaRepository = contaRepository;
        this.transferenciaRepository = transferenciaRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    public ExtratoResponse gerarExtrato(String numeroConta) {
        Conta conta = contaRepository.findByNumero(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta nao encontrada"));

        List<ExtratoItem> itens = new ArrayList<>();

        List<Transferencia> transferencias = transferenciaRepository.encontrarPorConta(numeroConta);
        for (Transferencia t : transferencias) {
            String tipo = t.getContaOrigem().equals(numeroConta) ? "DEBITO" : "CREDITO";
            String descricao = "Transferencia " + tipo.toLowerCase() + " - "
                    + (tipo.equals("DEBITO") ? t.getContaDestino() : t.getContaOrigem());
            itens.add(new ExtratoItem(
                    t.getId().toString(), tipo, descricao,
                    t.getValor(), t.getDataHora().toString(), t.getStatus()
            ));
        }

        itens.sort(Comparator.comparing(ExtratoItem::dataHora).reversed());

        return new ExtratoResponse(
                conta.getNumero(), conta.getTitular(), conta.getSaldo(), itens
        );
    }

    public List<ExtratoResponse> gerarExtratoCompleto(Long contaId) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta nao encontrada"));

        List<ExtratoItem> itens = new ArrayList<>();

        List<Transferencia> transferencias = transferenciaRepository.encontrarPorConta(conta.getNumero());
        for (Transferencia t : transferencias) {
            String tipo = t.getContaOrigem().equals(conta.getNumero()) ? "DEBITO" : "CREDITO";
            itens.add(new ExtratoItem(
                    t.getId().toString(), "TRANSFERENCIA",
                    "Transferencia " + tipo.toLowerCase(),
                    t.getValor(), t.getDataHora().toString(), t.getStatus()
            ));
        }

        List<Pagamento> pagamentos = pagamentoRepository.findByContaOrigemId(contaId);
        for (Pagamento p : pagamentos) {
            itens.add(new ExtratoItem(
                    p.getId().toString(), p.getMetodo(),
                    p.getDescricao() != null ? p.getDescricao() : "Pagamento " + p.getMetodo(),
                    p.getValor(), p.getDataHora().toString(), p.getStatus()
            ));
        }

        itens.sort(Comparator.comparing(ExtratoItem::dataHora).reversed());

        return List.of(new ExtratoResponse(
                conta.getNumero(), conta.getTitular(), conta.getSaldo(), itens
        ));
    }
}
