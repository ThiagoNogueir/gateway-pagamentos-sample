package com.banco.gatewaypagamentos.repository;

import com.banco.gatewaypagamentos.model.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BoletoRepository extends JpaRepository<Boleto, Long> {
    Optional<Boleto> findByCodigoBarras(String codigoBarras);
    List<Boleto> findByContaId(Long contaId);
    List<Boleto> findByStatus(String status);
    List<Boleto> findByPagamentoId(Long pagamentoId);
}
