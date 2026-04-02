package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Interface de acesso a dados para a entidade Servicos.
 * Gerencia o catálogo de serviços oferecidos, incluindo preços e durações.
 */
@Repository
public interface ServicosRepository extends JpaRepository<Servicos, UUID> {

    /**
     * Busca detalhada de um serviço validando todos os seus atributos principais.
     * Utilizado para evitar a criação de serviços duplicados com as mesmas características.
     */
    Servicos findByNomeServicoAndDescricaoServicoAndPrecoServicoAndDuracaoMinutos(
            String nomeServico, String descricaoServico, BigDecimal precoServico, Integer duracaoMinutos
    );

    /**
     * Retorna uma lista de serviços filtrados por ID, Nome e Preço.
     * Útil para cruzamento de dados em telas de edição ou relatórios de vendas.
     */
    List<Servicos> findByIdServicoAndNomeServicoAndPrecoServico(UUID id, String nome, BigDecimal preco);

    /**
     * Localiza um serviço pelo seu nome comercial.
     */
    Servicos findByNomeServico(String nome);

    /**
     * Filtra serviços que possuem um valor específico.
     * Pode ser utilizado para agrupar serviços em faixas de preço promocionais.
     */
    Servicos findByPrecoServico(BigDecimal preco);

    /**
     * Busca um serviço através de sua descrição textual.
     */
    Servicos findByDescricaoServico(String desc);

    /**
     * Localiza serviços com base no tempo estimado de execução.
     * Auxilia na organização da agenda conforme a disponibilidade de tempo.
     */
    Servicos findByDuracaoMinutos(Integer duracaoMinutos);

    /**
     * Remove um serviço do catálogo utilizando o nome como referência.
     * Requer @Transactional para garantir a integridade da operação no banco de dados.
     */
    @Transactional
    void deleteByNomeServico(String nome);
}