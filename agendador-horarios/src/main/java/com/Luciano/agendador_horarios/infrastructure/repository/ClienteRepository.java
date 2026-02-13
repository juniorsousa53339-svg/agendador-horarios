package com.Luciano.agendador_horarios.infrastructure.repository;


import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByNomeCliente(String nomeCliente);

    @Transactional
    void deleteByNomeCliente(String nomeCliente);

    Cliente findByTelefoneCliente(String telefoneCliente);

    List<Cliente> findByIdClienteAndNomeCliente(long idCliente, String nomeCliente);

}
