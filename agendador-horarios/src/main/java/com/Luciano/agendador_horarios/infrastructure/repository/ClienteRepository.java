package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByNomeClienteAndTelefoneCliente(String nomeCliente, String telefoneCliente);

    Cliente findByNomeCliente(String nomeCliente);

    Cliente findByTelefoneCliente(String telefoneCliente);

    List<Cliente> findByIdClienteAndNomeCliente(Long idCliente, String nomeCliente);

    void deleteByNomeCliente(String nomeCliente);
}