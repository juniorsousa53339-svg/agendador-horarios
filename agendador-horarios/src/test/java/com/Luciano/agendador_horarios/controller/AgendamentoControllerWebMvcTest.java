package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.DTO.AgendamentoRequestDTO;
import com.Luciano.agendador_horarios.DTO.AgendamentoResponseDTO;
import com.Luciano.agendador_horarios.service.AgendamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AgendamentoController.class)
class AgendamentoControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgendamentoService agendamentoService;

    private ObjectMapper om;

    @BeforeEach
    void setUp() {
        om = new ObjectMapper();

        om.registerModule(new JavaTimeModule());
    }

    @Test
    void deveCriarAgendamento() throws Exception {

        AgendamentoRequestDTO body = new AgendamentoRequestDTO(
                1L, 10L, 3L, LocalDateTime.parse("2026-02-23T18:30:00")
        );


        when(agendamentoService.criar(ArgumentMatchers.any()))
                .thenReturn(new AgendamentoResponseDTO(
                        55L, 1L, "João",
                        10L, "Carlos",
                        3L, "Corte Masculino",
                        LocalDateTime.parse("2026-02-23T18:30:00"),
                        "MARCADO"
                ));

        mockMvc.perform(
                post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body))
        ).andExpect(status().isCreated());
    }
}