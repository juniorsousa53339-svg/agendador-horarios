package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ServicosRepository extends JpaRepository<Servicos, Long> {

    Servicos findByNomeServicoAndDescricaoServico(String nomeServico, String descricaoServico);

    @Transactional
    void deleteByNomeServico(String nomeServico);

    List<Servicos> findByIdServicoAndNomeServicoAndPrecoServico(long idServico,
                                                                String nomeServico,
                                                                BigDecimal precoServico);

    Servicos findByNomeServico(String nomeServico);

    Servicos findByPrecoServico(BigDecimal precoServico);
}