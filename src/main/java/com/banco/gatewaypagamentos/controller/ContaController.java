package com.banco.gatewaypagamentos.controller;

import com.banco.gatewaypagamentos.dto.ContaRequest;
import com.banco.gatewaypagamentos.dto.ContaResponse;
import com.banco.gatewaypagamentos.service.ContaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService service;

    public ContaController(ContaService service) {
        this.service = service;
    }

    @PostMapping
    public ContaResponse criar(@RequestBody ContaRequest request) {
        return service.criar(request);
    }

    @GetMapping
    public List<ContaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ContaResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<ContaResponse> buscarPorCliente(@PathVariable Long clienteId) {
        return service.buscarPorCliente(clienteId);
    }

    @PutMapping("/{id}")
    public ContaResponse atualizar(@PathVariable Long id, @RequestBody ContaRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
