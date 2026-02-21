package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Agendamento;
import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import com.Luciano.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTests {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Test
    @DisplayName("Deve salvar agendamento quando não há conflito")
    void deveSalvarAgendamentoComSucesso() {
        Servicos servico = new Servicos();
        Cliente cliente = new Cliente();

        Agendamento agendamento = new Agendamento();
        agendamento.setServico(servico);
        agendamento.setCliente(cliente);
        agendamento.setDataHoraAgendamento(LocalDateTime.now());

        when(agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(anyString(), any(), any()))
                .thenReturn(null);
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        Agendamento resultado = agendamentoService.salvarAgendamento(agendamento);

        assertNotNull(resultado);
        verify(agendamentoRepository).save(agendamento);
    }

    @Test
    @DisplayName("Deve buscar agendamentos do dia")
    void deveBuscarAgendamentosDia() {
        Cliente cliente = new Cliente();
        LocalDate data = LocalDate.now();

        when(agendamentoRepository.findByCliete(cliente)).thenReturn(null);
        when(agendamentoRepository.findByDataHoraAgendamentoBetween(any(), any()))
                .thenReturn(List.of(new Agendamento()));

        List<Agendamento> resultado = agendamentoService.buscarAgendamentosDia(data, cliente);

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve lançar erro ao alterar agendamento inexistente")
    void deveLancarErroAoAlterarInexistente() {
        Cliente cliente = new Cliente();
        LocalDateTime horario = LocalDateTime.now();

        when(agendamentoRepository.findByDataHoraAgendamentoAndCliente(horario, cliente)).thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> agendamentoService.alterarAgendamento(new Agendamento(), cliente, horario));
        verify(agendamentoRepository, never()).save(any(Agendamento.class));
    }
}
