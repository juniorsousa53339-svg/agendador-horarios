package com.Luciano.agendador_horarios;

import com.Luciano.agendador_horarios.infrastructure.entity.Agendamento;
import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import com.Luciano.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import com.Luciano.agendador_horarios.service.AgendamentoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

//    @Test
//    @DisplayName("Deve salvar agendamento com sucesso")
//    void deveSalvarAgendamentoComSucesso() {
//
//
//        Agendamento agendamento = new Agendamento();
//        agendamento.setServico(1L,"Corte","Americano",35);
//        agendamento.setDataHoraAgendamento(LocalDateTime.now());
//
//        when(agendamentoRepository
//                .findByServicoAndDataHoraAgendamentoBetween(anyString(), any(), any()))
//                .thenReturn(null);
//
//        when(agendamentoRepository.save(any())).thenReturn(agendamento);
//
//        Agendamento resultado = agendamentoService.salvarAgendamento(agendamento);
//
//        assertNotNull(resultado);
//        verify(agendamentoRepository, times(1)).save(agendamento);
//    }

    @Test
    @DisplayName("Deve deletar agendamento com sucesso")
    void deveDeletarAgendamentoComSucesso() {

        LocalDateTime dataHora = LocalDateTime.now();
        String cliente = "Luciano";

        agendamentoService.deletarAgendamento(dataHora, cliente);

        verify(agendamentoRepository, times(1))
                .deleteByDataHoraAgendamentoAndCliente(dataHora, cliente);
    }

    @Test
    @DisplayName("Deve buscar agendamentos do dia")
    void deveBuscarAgendamentosDiaComSucesso() {

        LocalDate data = LocalDate.now();

        when(agendamentoRepository.findByDataHoraAgendamentoBetween(any(), any()))
                .thenReturn(List.of(new Agendamento()));

        List<Agendamento> resultado = agendamentoService.buscarAgendamentosDia(data);

        assertNotNull(resultado);
        verify(agendamentoRepository, times(1))
                .findByDataHoraAgendamentoBetween(any(), any());
    }

    @Test
    @DisplayName("Deve alterar agendamento com sucesso")
    void deveAlterarAgendamentoComSucesso() {

        LocalDateTime dataHora = LocalDateTime.now();
        String cliente = "Luciano";

        Agendamento existente = new Agendamento();
        existente.setIdAgendamento(1L);

        Agendamento novoAgendamento = new Agendamento();

        when(agendamentoRepository
                .findByDataHoraAgendamentoAndCliente(dataHora, cliente))
                .thenReturn(existente);

        when(agendamentoRepository.save(any())).thenReturn(novoAgendamento);

        Agendamento resultado =
                agendamentoService.alterarAgendamento(novoAgendamento, cliente, dataHora);

        assertNotNull(resultado);
        verify(agendamentoRepository, times(1)).save(novoAgendamento);
    }
}