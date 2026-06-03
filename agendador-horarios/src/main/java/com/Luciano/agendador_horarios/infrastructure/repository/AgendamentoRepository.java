package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Agendamento;
import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {


    boolean existsByFuncionarioAndDataHoraAgendamento(Funcionario funcionario, LocalDateTime dataHora);

    List<Agendamento> findByClienteAndDataHoraAgendamentoBetween(
            Cliente cliente,
            LocalDateTime inicio,
            LocalDateTime fim
    );


    Agendamento findByDataHoraAgendamentoAndCliente(LocalDateTime dataHora, Cliente cliente);


    void deleteByDataHoraAgendamentoAndCliente(LocalDateTime dataHora, Cliente cliente);


   boolean existsByFuncionario_IdFuncionarioAndDataHoraAgendamento(UUID idFuncionario, LocalDateTime dataHora);

   
   List<Agendamento>  findByFuncionario_IdFuncionarioAndDataHoraAgendamentoBetween(

          UUID idFuncionario,
            LocalDateTime inicio,
            LocalDateTime fim
   );


}
