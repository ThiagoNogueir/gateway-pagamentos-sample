package com.banco.gatewaypagamentos.repository;

import com.banco.gatewaypagamentos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByContaOrigemId(Long contaOrigemId);
    List<Pagamento> findByContaDestinoId(Long contaDestinoId);
    List<Pagamento> findByStatus(String status);
    List<Pagamento> findByMetodo(String metodo);
}
