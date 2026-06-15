package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Repository
public interface ServicosRepository extends JpaRepository<Servicos, UUID> {

    Servicos findByNomeServicoAndDescricaoServicoAndPrecoServicoAndDuracaoMinutos(
            String nomeServico, String descricaoServico, BigDecimal precoServico, Integer duracaoMinutos
    );

    List<Servicos> findByIdServicoAndNomeServicoAndPrecoServico(UUID id, String nome, BigDecimal preco);

    Servicos findByNomeServico(String nome);

    @Transactional
    void deleteByNomeServico(String nome);


}