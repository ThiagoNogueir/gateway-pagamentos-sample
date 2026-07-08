package com.banco.gatewaypagamentos.service;

import com.banco.gatewaypagamentos.dto.ClienteRequest;
import com.banco.gatewaypagamentos.dto.ClienteResponse;
import com.banco.gatewaypagamentos.model.Cliente;
import com.banco.gatewaypagamentos.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ClienteResponse criar(ClienteRequest request) {
        if (repository.findByCpfCnpj(request.cpfCnpj()).isPresent()) {
            throw new RuntimeException("Cliente ja cadastrado com este CPF/CNPJ");
        }
        Cliente cliente = new Cliente(request.nome(), request.cpfCnpj(), request.email(), request.telefone(), request.tipo());
        return toResponse(repository.save(cliente));
    }

    public List<ClienteResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ClienteResponse buscarPorId(Long id) {
        return toResponse(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado")));
    }

    @Transactional
    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));
        cliente.setNome(request.nome());
        cliente.setEmail(request.email());
        cliente.setTelefone(request.telefone());
        return toResponse(repository.save(cliente));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente nao encontrado");
        }
        repository.deleteById(id);
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(), cliente.getNome(), cliente.getCpfCnpj(),
                cliente.getEmail(), cliente.getTelefone(), cliente.getTipo(),
                cliente.getDataHora().toString()
        );
    }
}
