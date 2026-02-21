package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.service.ClienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(controllers = ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClienteControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    @DisplayName("POST /clientes deve retornar 202")
    void deveSalvarCliente() throws Exception {
        when(clienteService.salvarCliente(any(Cliente.class))).thenReturn(new Cliente());

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nomeCliente": "João",
                                  "telefoneCliente": "11999999999"
                                }
                                """))
                .andExpect(status().isAccepted());

        verify(clienteService).salvarCliente(any(Cliente.class));
    }

    @Test
    @DisplayName("DELETE /clientes deve retornar 204")
    void deveDeletarCliente() throws Exception {
        String nomeCliente = "João";

        mockMvc.perform(delete("/clientes")
                        .param("nomeCliente", nomeCliente))
                .andExpect(status().isNoContent());

        verify(clienteService).deletarCliente(nomeCliente);
    }

    @Test
    @DisplayName("GET /clientes deve retornar 200")
    void deveBuscarCliente() throws Exception {
        long idCliente = 1L;
        String nomeCliente = "João";

        when(clienteService.buscarCliente(idCliente, nomeCliente))
                .thenReturn(List.of(new Cliente()));

        mockMvc.perform(get("/clientes")
                        .param("idCliente", String.valueOf(idCliente))
                        .param("nomeCliente", nomeCliente))
                .andExpect(status().isOk());

        verify(clienteService).buscarCliente(idCliente, nomeCliente);
    }

    @Test
    @DisplayName("PUT /clientes/alterar-nome deve retornar 202")
    void deveAlterarNomeCliente() throws Exception {
        String nomeCliente = "João Atualizado";

        when(clienteService.alterarNomeCliente(any(Cliente.class), eq(nomeCliente)))
                .thenReturn(new Cliente());

        mockMvc.perform(put("/clientes/alterar-nome")
                        .param("nomeCliente", nomeCliente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nomeCliente": "João"
                                }
                                """))
                .andExpect(status().isAccepted());

        verify(clienteService).alterarNomeCliente(any(Cliente.class), eq(nomeCliente));
    }
}
