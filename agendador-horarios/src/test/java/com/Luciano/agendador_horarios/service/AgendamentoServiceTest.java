package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Agendamento;
import com.Luciano.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Test
    void salvarAgendamento_deveLancarExcecaoQuandoHorarioJaPreenchido() {
        Agendamento agendamento = new Agendamento();
        LocalDateTime dataHora = LocalDateTime.of(2025, 1, 10, 10, 0);
        agendamento.setDataHoraAgendamento(dataHora);

        when(agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(anyString(), eq(dataHora), eq(dataHora.plusMinutes(1))))
                .thenReturn(new Agendamento());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> agendamentoService.salvarAgendamento(agendamento));

        assertEquals("Horário já está preenchido", exception.getMessage());
        verify(agendamentoRepository, never()).save(any(Agendamento.class));
    }

    @Test
    void salvarAgendamento_deveSalvarQuandoHorarioDisponivel() {
        Agendamento agendamento = new Agendamento();
        LocalDateTime dataHora = LocalDateTime.of(2025, 1, 10, 10, 0);
        agendamento.setDataHoraAgendamento(dataHora);

        when(agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(anyString(), eq(dataHora), eq(dataHora.plusMinutes(1))))
                .thenReturn(null);
        when(agendamentoRepository.save(agendamento)).thenReturn(agendamento);

        Agendamento resultado = agendamentoService.salvarAgendamento(agendamento);

        assertSame(agendamento, resultado);
        verify(agendamentoRepository).save(agendamento);
    }

    @Test
    void buscarAgendamentosDia_deveBuscarEntreInicioEFimDoDia() {
        LocalDate data = LocalDate.of(2025, 1, 20);
        LocalDateTime inicioDia = data.atStartOfDay();
        LocalDateTime fimDia = data.atTime(20, 59, 59);
        List<Agendamento> esperado = List.of(new Agendamento());

        when(agendamentoRepository.findByDataHoraAgendamentoBetween(inicioDia, fimDia)).thenReturn(esperado);

        List<Agendamento> resultado = agendamentoService.buscarAgendamentosDia(data);

        assertEquals(esperado, resultado);
        verify(agendamentoRepository).findByDataHoraAgendamentoBetween(inicioDia, fimDia);
    }

    @Test
    void alterarAgendamento_deveManterIdDoAgendamentoOriginal() {
        String cliente = "Maria";
        LocalDateTime dataHoraOriginal = LocalDateTime.of(2025, 2, 1, 9, 30);

        Agendamento existente = new Agendamento();
        existente.setIdAgendamento(7L);

        Agendamento novoAgendamento = new Agendamento();

        when(agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraOriginal, cliente)).thenReturn(existente);
        when(agendamentoRepository.save(novoAgendamento)).thenReturn(novoAgendamento);

        Agendamento resultado = agendamentoService.alterarAgendamento(novoAgendamento, cliente, dataHoraOriginal);

        assertEquals(7L, novoAgendamento.getIdAgendamento());
        assertSame(novoAgendamento, resultado);
        verify(agendamentoRepository).save(novoAgendamento);
    }
}
