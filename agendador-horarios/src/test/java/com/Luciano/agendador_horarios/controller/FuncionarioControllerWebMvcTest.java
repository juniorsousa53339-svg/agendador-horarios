package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.service.FuncionarioService;
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

@WebMvcTest(controllers = FuncionarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class FuncionarioControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioService funcionarioService;

    @Test
    @DisplayName("POST /funcionarios deve retornar 202")
    void deveSalvarFuncionario() throws Exception {
        when(funcionarioService.salvarFuncionario(any(Funcionario.class))).thenReturn(new Funcionario());

        mockMvc.perform(post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nomeFuncionario": "Carlos",
                                  "telefoneFuncionario": "11988888888",
                                  "especialidade": "Corte"
                                }
                                """))
                .andExpect(status().isAccepted());

        verify(funcionarioService).salvarFuncionario(any(Funcionario.class));
    }

    @Test
    @DisplayName("DELETE /funcionarios deve retornar 204")
    void deveDeletarFuncionario() throws Exception {
        String nomeFuncionario = "Carlos";

        mockMvc.perform(delete("/funcionarios")
                        .param("nomeFuncionario", nomeFuncionario))
                .andExpect(status().isNoContent());

        verify(funcionarioService).deletarFuncionario(nomeFuncionario);
    }

    @Test
    @DisplayName("GET /funcionarios deve retornar 200")
    void deveBuscarFuncionario() throws Exception {
        long idFuncionario = 1L;
        String nomeFuncionario = "Carlos";

        when(funcionarioService.buscarFuncionario(idFuncionario, nomeFuncionario))
                .thenReturn(List.of(new Funcionario()));

        mockMvc.perform(get("/funcionarios")
                        .param("idFuncionario", String.valueOf(idFuncionario))
                        .param("nomeFuncionario", nomeFuncionario))
                .andExpect(status().isOk());

        verify(funcionarioService).buscarFuncionario(idFuncionario, nomeFuncionario);
    }

    @Test
    @DisplayName("PUT /funcionarios/alterar-nome deve retornar 202")
    void deveAlterarNomeFuncionario() throws Exception {
        String nomeFuncionario = "Carlos Atualizado";

        when(funcionarioService.alterarNomeFuncionario(any(Funcionario.class), eq(nomeFuncionario)))
                .thenReturn(new Funcionario());

        mockMvc.perform(put("/funcionarios/alterar-nome")
                        .param("nomeFuncionario", nomeFuncionario)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nomeFuncionario": "Carlos"
                                }
                                """))
                .andExpect(status().isAccepted());

        verify(funcionarioService).alterarNomeFuncionario(any(Funcionario.class), eq(nomeFuncionario));
    }
}
