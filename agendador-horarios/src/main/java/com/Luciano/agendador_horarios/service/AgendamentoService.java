package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Agendamento;
import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public Agendamento salvarAgendamento(Agendamento agendamento) {

        LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
        LocalDateTime horaFim = agendamento.getDataHoraAgendamento().plusMinutes(1);

        Agendamento agendados =
                agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(
                        String.valueOf(agendamento.getServico())
                        , horaAgendamento
                        , horaFim
                );

        if (Objects.nonNull(agendados)) {
            throw new RuntimeException("Horário já está preenchido");
        }

        return agendamentoRepository.save(agendamento);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public void deletarAgendamento(
            LocalDateTime dataHoraAgendamento,
            Cliente cliente
    ) {

      Agendamento agendamento = agendamentoRepository.findByCliente(cliente);

       if (Objects.nonNull(agendamento)) {
           throw new RuntimeException("Agendamento não encontrado!");
       }

        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Agendamento> buscarAgendamentosDia(
            LocalDate data,
          Cliente cliente
    ) {

        Agendamento agendamento =
                agendamentoRepository.findByCliente(cliente);

        if (Objects.nonNull(agendamento)) {
            throw new RuntimeException("Agendamento não encontrado!");
        }

        LocalDateTime primeiraHoraDia = data.atStartOfDay();
        LocalDateTime horaFinalDia = data.atTime(20, 59, 59);

        return agendamentoRepository.findByDataHoraAgendamentoBetween(primeiraHoraDia, horaFinalDia);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public Agendamento alterarAgendamento(
            Agendamento agendamento,
            Cliente cliente,
            LocalDateTime dataHoraAgendamento
    ) {
        Agendamento agenda = agendamentoRepository.
                findByDataHoraAgendamentoAndCliente(
                dataHoraAgendamento, cliente
        );

        if (Objects.nonNull(agenda)) {
            throw new RuntimeException("Horário não está preenchido");
        }

        agendamento.setIdAgendamento(agenda.getIdAgendamento());
        return agendamentoRepository.save(agendamento);
    }
}