package com.banco.gatewaypagamentos.controller;

import com.banco.gatewaypagamentos.dto.PagamentoRequest;
import com.banco.gatewaypagamentos.dto.PagamentoResponse;
import com.banco.gatewaypagamentos.service.PagamentoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @PostMapping
    public PagamentoResponse processar(@RequestBody PagamentoRequest request) {
        return service.processar(request);
    }

    @GetMapping
    public List<PagamentoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PagamentoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/conta/{contaId}")
    public List<PagamentoResponse> buscarPorConta(@PathVariable Long contaId) {
        return service.buscarPorConta(contaId);
    }
}
