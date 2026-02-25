package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ServicosRepository extends JpaRepository<Servicos, Long> {

    Servicos findByNomeServicoAndDescricaoServicoAndPrecoServico(
            String nomeServico, String descricaoServico, BigDecimal precoServico
    );

    // (mantenha os demais)
    List<Servicos> findByIdServicoAndNomeServicoAndPrecoServico(Long id, String nome, BigDecimal preco);
    Servicos findByNomeServico(String nome);
    Servicos findByPrecoServico(BigDecimal preco);
    Servicos findByDescricaoServico(String desc);
    void deleteByNomeServico(String nome);
}