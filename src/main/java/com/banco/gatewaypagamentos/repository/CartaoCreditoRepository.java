package com.banco.gatewaypagamentos.repository;

import com.banco.gatewaypagamentos.model.CartaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {
    List<CartaoCredito> findByContaId(Long contaId);
    List<CartaoCredito> findByBandeira(String bandeira);
    List<CartaoCredito> findByStatus(String status);
}
