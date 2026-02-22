package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Agendamento;
import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    Agendamento findByServicoAndDataHoraAgendamentoBetween(Servicos servico, LocalDateTime dataHoraInicio,
                                                           Cliente dataHoraFinal);

    @Transactional
    void deleteByDataHoraAgendamentoAndCliente(LocalDateTime dataHoraAgendamento, Cliente cliente);

    List<Agendamento> findByDataHoraAgendamentoBetween(LocalDateTime dataHoraInicial, LocalDateTime dataHoraFinal);

    Agendamento findByDataHoraAgendamentoAndCliente(LocalDateTime dataHoraAgendamento, Cliente cliente);

    List<Agendamento> findByCliete(Cliente cliente);



    Agendamento findByServicoAndDataHoraAgendamentoAndClienteBetween(Servicos servico, LocalDateTime dataHoraAgendamento, Cliente cliente);

    Agendamento findByAndNomeCliete(Cliente cliente);
}