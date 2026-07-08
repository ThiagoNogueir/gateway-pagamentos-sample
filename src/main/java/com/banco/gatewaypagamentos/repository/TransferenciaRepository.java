package com.banco.gatewaypagamentos.repository;

import com.banco.gatewaypagamentos.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    @Query("SELECT SUM(t.valor) FROM Transferencia t WHERE t.contaDestino = :conta AND t.status = 'PROCESSANDO'")
    Double somarEntradas(@Param("conta") String conta);

    @Query("SELECT SUM(t.valor) FROM Transferencia t WHERE t.contaOrigem = :conta AND t.status = 'PROCESSANDO'")
    Double somarSaidas(@Param("conta") String conta);

    @Query("SELECT t FROM Transferencia t WHERE t.contaOrigem = :conta OR t.contaDestino = :conta ORDER BY t.dataHora DESC")
    List<Transferencia> encontrarPorConta(@Param("conta") String conta);
}

