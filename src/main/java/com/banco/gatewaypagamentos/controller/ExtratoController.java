package com.banco.gatewaypagamentos.controller;

import com.banco.gatewaypagamentos.dto.ExtratoResponse;
import com.banco.gatewaypagamentos.service.ExtratoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/extratos")
public class ExtratoController {

    private final ExtratoService service;

    public ExtratoController(ExtratoService service) {
        this.service = service;
    }

    @GetMapping("/{numeroConta}")
    public ExtratoResponse gerarExtrato(@PathVariable String numeroConta) {
        return service.gerarExtrato(numeroConta);
    }

    @GetMapping("/conta/{contaId}")
    public List<ExtratoResponse> gerarExtratoCompleto(@PathVariable Long contaId) {
        return service.gerarExtratoCompleto(contaId);
    }
}
