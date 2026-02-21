package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Agendamento;
import com.Luciano.agendador_horarios.service.AgendamentoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AgendamentoController.class)
@AutoConfigureMockMvc(addFilters = false)
class AgendamentoControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgendamentoService agendamentoService;

    @Test
    @DisplayName("POST /agendamentos deve retornar 202")
    void deveSalvarAgendamento() throws Exception {
        when(agendamentoService.salvarAgendamento(any(Agendamento.class)))
                .thenReturn(new Agendamento());

        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dataHoraAgendamento": "2026-01-10T10:00:00"
                                }
                                """))
                .andExpect(status().isAccepted());

        verify(agendamentoService).salvarAgendamento(any(Agendamento.class));
    }

    @Test
    @DisplayName("DELETE /agendamentos deve retornar 204")
    void deveDeletarAgendamento() throws Exception {
        String cliente = "Luciano";
        LocalDateTime dataHora = LocalDateTime.of(2026, 1, 10, 10, 0);

        mockMvc.perform(delete("/agendamentos")
                        .param("cliente", cliente)
                        .param("dataHoraAgendamento", dataHora.toString()))
                .andExpect(status().isNoContent());

        verify(agendamentoService).deletarAgendamento(dataHora, cliente);
    }

    @Test
    @DisplayName("GET /agendamentos deve retornar 200")
    void deveBuscarAgendamentosDia() throws Exception {
        LocalDate data = LocalDate.of(2026, 1, 10);
        String cliente = "Luciano";

        when(agendamentoService.buscarAgendamentosDia(eq(data), eq(cliente)))
                .thenReturn(List.of(new Agendamento()));

        mockMvc.perform(get("/agendamentos")
                        .param("data", data.toString())
                        .param("cliente", cliente))
                .andExpect(status().isOk());

        verify(agendamentoService).buscarAgendamentosDia(data, cliente);
    }

    @Test
    @DisplayName("PUT /agendamentos deve retornar 202")
    void deveAlterarAgendamento() throws Exception {
        String cliente = "Luciano";
        LocalDateTime dataHora = LocalDateTime.of(2026, 1, 10, 10, 0);

        when(agendamentoService.alterarAgendamento(any(Agendamento.class), eq(cliente), eq(dataHora)))
                .thenReturn(new Agendamento());

        mockMvc.perform(put("/agendamentos")
                        .param("cliente", cliente)
                        .param("dataHoraAgendamento", dataHora.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dataHoraAgendamento": "2026-01-11T11:00:00"
                                }
                                """))
                .andExpect(status().isAccepted());

        verify(agendamentoService).alterarAgendamento(any(Agendamento.class), eq(cliente), eq(dataHora));
    }
}
