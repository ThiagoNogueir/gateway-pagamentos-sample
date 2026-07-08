package com.banco.gatewaypagamentos.service;

import com.banco.gatewaypagamentos.dto.ContaRequest;
import com.banco.gatewaypagamentos.dto.ContaResponse;
import com.banco.gatewaypagamentos.model.Cliente;
import com.banco.gatewaypagamentos.model.Conta;
import com.banco.gatewaypagamentos.repository.ClienteRepository;
import com.banco.gatewaypagamentos.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ContaResponse criar(ContaRequest request) {
        if (contaRepository.findByNumero(request.numero()).isPresent()) {
            throw new RuntimeException("Conta ja cadastrada com este numero");
        }

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado com id: " + request.clienteId()));

        Conta conta = new Conta(
                request.numero(), request.titular(), request.banco(),
                request.agencia(), request.tipo(), request.saldo(),
                request.status(), cliente.getId()
        );
        return toResponse(contaRepository.save(conta));
    }

    public List<ContaResponse> listar() {
        return contaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ContaResponse buscarPorId(Long id) {
        return toResponse(contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta nao encontrada")));
    }

    public List<ContaResponse> buscarPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new RuntimeException("Cliente nao encontrado com id: " + clienteId);
        }
        return contaRepository.findByClienteId(clienteId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public ContaResponse atualizar(Long id, ContaRequest request) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta nao encontrada"));

        if (request.clienteId() != null
                && !conta.getClienteId().equals(request.clienteId())
                && !clienteRepository.existsById(request.clienteId())) {
            throw new RuntimeException("Cliente nao encontrado com id: " + request.clienteId());
        }

        conta.setNumero(request.numero());
        conta.setTitular(request.titular());
        conta.setBanco(request.banco());
        conta.setAgencia(request.agencia());
        conta.setTipo(request.tipo());
        conta.setSaldo(request.saldo());
        conta.setStatus(request.status());
        conta.setClienteId(request.clienteId());
        return toResponse(contaRepository.save(conta));
    }

    @Transactional
    public void deletar(Long id) {
        if (!contaRepository.existsById(id)) {
            throw new RuntimeException("Conta nao encontrada");
        }
        contaRepository.deleteById(id);
    }

    private ContaResponse toResponse(Conta conta) {
        return new ContaResponse(
                conta.getId(), conta.getNumero(), conta.getTitular(),
                conta.getBanco(), conta.getAgencia(), conta.getTipo(),
                conta.getSaldo(), conta.getStatus(), conta.getClienteId(),
                conta.getDataHora().toString()
        );
    }
}
