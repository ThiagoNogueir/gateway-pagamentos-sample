package com.banco.gatewaypagamentos.repository;

import com.banco.gatewaypagamentos.model.Pix;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PixRepository extends JpaRepository<Pix, Long> {
    Optional<Pix> findByChave(String chave);
    List<Pix> findByContaId(Long contaId);
    List<Pix> findByStatus(String status);
}
