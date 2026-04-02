package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Interface de acesso a dados para a entidade Cliente.
 * Centraliza as operações de busca e gestão de informações dos clientes da barbearia.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    /**
     * Localiza um cliente utilizando a combinação de nome e telefone.
     * Utilizado para garantir a identificação precisa em casos de nomes comuns.
     */
    Cliente findByNomeClienteAndTelefoneCliente(String nomeCliente, String telefoneCliente);

    /**
     * Realiza a busca de um cliente apenas pelo nome.
     */
    Cliente findByNomeCliente(String nomeCliente);

    /**
     * Busca um cliente pelo número de telefone.
     * Geralmente utilizado como identificador rápido ou para validação de cadastro.
     */
    Cliente findByTelefoneCliente(String telefoneCliente);

    /**
     * Retorna uma lista de clientes que correspondam ao ID e Nome fornecidos.
     * Útil para procedimentos de auditoria ou telas de confirmação de dados.
     */
    List<Cliente> findByIdClienteAndNomeCliente(UUID idCliente, String nomeCliente);

    /**
     * Remove um cliente do sistema com base no nome informado.
     * A anotação @Transactional é obrigatória para operações de deleção customizadas.
     */
    @Transactional
    void deleteByNomeCliente(String nomeCliente);
}