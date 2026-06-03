package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Cliente findByNomeClienteAndTelefoneCliente(String nomeCliente, String telefoneCliente);

    Cliente findByNomeCliente(String nomeCliente);

    Cliente findByTelefoneCliente(String telefoneCliente);

    List<Cliente> findByIdClienteAndNomeCliente(UUID idCliente, String nomeCliente);

    @Transactional
    void deleteByNomeCliente(String nomeCliente);
}