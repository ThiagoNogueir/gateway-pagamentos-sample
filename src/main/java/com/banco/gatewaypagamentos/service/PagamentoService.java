package com.banco.gatewaypagamentos.service;

import com.banco.gatewaypagamentos.dto.PagamentoRequest;
import com.banco.gatewaypagamentos.dto.PagamentoResponse;
import com.banco.gatewaypagamentos.model.*;
import com.banco.gatewaypagamentos.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ContaRepository contaRepository;
    private final PixRepository pixRepository;
    private final BoletoRepository boletoRepository;
    private final CartaoCreditoRepository cartaoCreditoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, ContaRepository contaRepository,
                            PixRepository pixRepository, BoletoRepository boletoRepository,
                            CartaoCreditoRepository cartaoCreditoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.contaRepository = contaRepository;
        this.pixRepository = pixRepository;
        this.boletoRepository = boletoRepository;
        this.cartaoCreditoRepository = cartaoCreditoRepository;
    }

    @Transactional
    public PagamentoResponse processar(PagamentoRequest request) {
        if (request.valor() <= 0) {
            throw new RuntimeException("Valor invalido");
        }

        Conta origem = contaRepository.findById(request.contaOrigemId())
                .orElseThrow(() -> new RuntimeException("Conta origem nao encontrada"));

        if (!"ATIVA".equals(origem.getStatus())) {
            throw new RuntimeException("Conta origem nao esta ativa");
        }

        if (origem.getSaldo() < request.valor()) {
            throw new RuntimeException("Saldo insuficiente");
        }

        Conta destino = contaRepository.findById(request.contaDestinoId())
                .orElseThrow(() -> new RuntimeException("Conta destino nao encontrada"));

        if (!"ATIVA".equals(destino.getStatus())) {
            throw new RuntimeException("Conta destino nao esta ativa");
        }

        Pagamento pagamento = new Pagamento(
                request.contaOrigemId(), request.contaDestinoId(),
                request.valor(), "PROCESSANDO", request.metodo(), request.descricao()
        );
        pagamento = pagamentoRepository.save(pagamento);

        switch (request.metodo().toUpperCase()) {
            case "PIX" -> processarPix(pagamento, request);
            case "BOLETO" -> processarBoleto(pagamento, request);
            case "CARTAO" -> processarCartao(pagamento, request);
            default -> throw new RuntimeException("Metodo de pagamento invalido: " + request.metodo());
        }

        origem.setSaldo(origem.getSaldo() - request.valor());
        destino.setSaldo(destino.getSaldo() + request.valor());
        contaRepository.save(origem);
        contaRepository.save(destino);

        pagamento.setStatus("CONCLUIDO");
        pagamento = pagamentoRepository.save(pagamento);

        return toResponse(pagamento);
    }

    private void processarPix(Pagamento pagamento, PagamentoRequest request) {
        if (request.chavePix() == null || request.chavePix().isBlank()) {
            throw new RuntimeException("Chave Pix obrigatoria");
        }
        Pix pix = new Pix(
                request.contaDestinoId(), request.chavePix(),
                request.tipoChavePix(), "ATIVA"
        );
        pixRepository.save(pix);
    }

    private void processarBoleto(Pagamento pagamento, PagamentoRequest request) {
        if (request.codigoBarrasBoleto() == null || request.codigoBarrasBoleto().isBlank()) {
            throw new RuntimeException("Codigo de barras obrigatorio");
        }
        Boleto boleto = new Boleto(
                pagamento.getId(), request.contaDestinoId(), request.codigoBarrasBoleto(),
                "", "", request.valor(),
                request.dataVencimentoBoleto() != null ? request.dataVencimentoBoleto() : LocalDate.now().plusDays(30),
                "PENDENTE"
        );
        boletoRepository.save(boleto);
    }

    private void processarCartao(Pagamento pagamento, PagamentoRequest request) {
        if (request.cartaoCreditoId() == null) {
            throw new RuntimeException("Cartao de credito obrigatorio");
        }
        CartaoCredito cartao = cartaoCreditoRepository.findById(request.cartaoCreditoId())
                .orElseThrow(() -> new RuntimeException("Cartao nao encontrado"));

        double valorParcela = request.parcelas() != null && request.parcelas() > 1
                ? request.valor() / request.parcelas() : request.valor();

        if (cartao.getUtilizado() + valorParcela > cartao.getLimite()) {
            throw new RuntimeException("Limite do cartao insuficiente");
        }

        cartao.setUtilizado(cartao.getUtilizado() + request.valor());
        cartaoCreditoRepository.save(cartao);
    }

    public List<PagamentoResponse> listar() {
        return pagamentoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public PagamentoResponse buscarPorId(Long id) {
        return toResponse(pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento nao encontrado")));
    }

    public List<PagamentoResponse> buscarPorConta(Long contaId) {
        return pagamentoRepository.findByContaOrigemId(contaId).stream().map(this::toResponse).toList();
    }

    private PagamentoResponse toResponse(Pagamento p) {
        return new PagamentoResponse(
                p.getId(), p.getContaOrigemId(), p.getContaDestinoId(),
                p.getValor(), p.getStatus(), p.getMetodo(),
                p.getDescricao(), p.getDataHora().toString()
        );
    }
}
