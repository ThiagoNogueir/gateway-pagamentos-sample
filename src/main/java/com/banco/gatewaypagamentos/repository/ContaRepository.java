package com.banco.gatewaypagamentos.repository;

import com.banco.gatewaypagamentos.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumero(String numero);
    List<Conta> findByClienteId(Long clienteId);
    List<Conta> findByStatus(String status);
}
