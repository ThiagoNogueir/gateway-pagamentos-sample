package com.banco.gatewaypagamentos.service;

import com.banco.gatewaypagamentos.dto.TransferenciaRequest;
import com.banco.gatewaypagamentos.dto.TransferenciaResponse;
import com.banco.gatewaypagamentos.model.Conta;
import com.banco.gatewaypagamentos.model.Transferencia;
import com.banco.gatewaypagamentos.repository.ContaRepository;
import com.banco.gatewaypagamentos.repository.TransferenciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final ContaRepository contaRepository;

    public TransferenciaService(TransferenciaRepository transferenciaRepository, ContaRepository contaRepository) {
        this.transferenciaRepository = transferenciaRepository;
        this.contaRepository = contaRepository;
    }

    @Transactional
    public TransferenciaResponse processar(TransferenciaRequest request) {

        if (request.valor() <= 0) {
            throw new RuntimeException("Valor invalido ou nao informado.");
        }

        if (request.valor() > 10000) {
            throw new RuntimeException("Valor excede o limite permitido.");
        }

        Conta origem = contaRepository.findByNumero(request.contaOrigem())
                .orElseThrow(() -> new RuntimeException("Conta origem nao encontrada: " + request.contaOrigem()));

        Conta destino = contaRepository.findByNumero(request.contaDestino())
                .orElseThrow(() -> new RuntimeException("Conta destino nao encontrada: " + request.contaDestino()));

        if (!"ATIVA".equals(origem.getStatus())) {
            throw new RuntimeException("Conta origem nao esta ativa");
        }

        if (!"ATIVA".equals(destino.getStatus())) {
            throw new RuntimeException("Conta destino nao esta ativa");
        }

        if (origem.getSaldo() < request.valor()) {
            throw new RuntimeException("Saldo insuficiente na conta origem");
        }

        origem.setSaldo(origem.getSaldo() - request.valor());
        destino.setSaldo(destino.getSaldo() + request.valor());
        contaRepository.save(origem);
        contaRepository.save(destino);

        Transferencia transferencia = new Transferencia(
                request.contaOrigem(), request.contaDestino(),
                request.valor(), "CONCLUIDO"
        );
        transferencia = transferenciaRepository.save(transferencia);

        return new TransferenciaResponse(
                transferencia.getId().toString(),
                transferencia.getStatus(),
                "Transferencia realizada com sucesso"
        );
    }

    public double consultarSaldo(String numeroConta) {
        Conta conta = contaRepository.findByNumero(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta nao encontrada: " + numeroConta));
        return conta.getSaldo();
    }

}

