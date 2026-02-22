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

        Agendamento agendados =
                agendamentoRepository.findByServicoAndDataHoraAgendamentoAndClienteBetween(

                       agendamento.getServico(),
                        agendamento.getDataHoraAgendamento(),
                        agendamento.getCliente()
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

      Agendamento agendamento =
              agendamentoRepository.findByAndNomeCliete(cliente);

       if (Objects.nonNull(agendamento)) {
           throw new RuntimeException("Agendamento não encontrado!");
       }

        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente
                (dataHoraAgendamento, cliente);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Agendamento> buscarAgendamentosDia(
            LocalDate data,
          Cliente cliente
    ) {

       List <Agendamento> agendamento =
                agendamentoRepository.findByCliete(cliente);

        if (Objects.nonNull(agendamento)) {
            throw new RuntimeException("Agendamento não encontrado!");
        }

        return agendamento;
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")

    public Agendamento alterarAgendamento(
            Agendamento agendamento,
            Cliente clienteAtual,
            Cliente clienteNovo,
            LocalDateTime dataHoraAgendamentoAtual,
            LocalDateTime dataHoraAgendamentoNovo

    ) {
        Agendamento agenda =
                agendamentoRepository.findByDataHoraAgendamentoAndCliente(
                dataHoraAgendamentoAtual, clienteAtual
        );

        if (Objects.isNull(agenda)) {
            throw new RuntimeException("Horário não está preenchido");
        }

        agenda.setDataHoraAgendamento(dataHoraAgendamentoNovo);
        agenda.setCliente(clienteNovo);

        return agendamentoRepository.save(agenda);
    }
}