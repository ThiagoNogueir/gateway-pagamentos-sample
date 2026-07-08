package com.banco.gatewaypagamentos.controller;

import com.banco.gatewaypagamentos.dto.TransferenciaRequest;
import com.banco.gatewaypagamentos.dto.TransferenciaResponse;
import com.banco.gatewaypagamentos.service.TransferenciaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {


    private final TransferenciaService service;

    public TransferenciaController(TransferenciaService service) {
        this.service = service;
    }

    @PostMapping
    public TransferenciaResponse realizarTransferencia(@RequestBody TransferenciaRequest request) {
        return service.processar(request);
    }

    @GetMapping("/saldo/{conta}")
    public double buscarSaldo(@PathVariable String conta) {
        return service.consultarSaldo(conta);
}



}






