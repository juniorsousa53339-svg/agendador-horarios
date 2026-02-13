package com.Luciano.agendador_horarios;

import com.Luciano.agendador_horarios.controller.BarbeariaController;
import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.service.BarbeariaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BarbeariaControllerTest {

    private BarbeariaService barbeariaService;
    private BarbeariaController barbeariaController;

    @BeforeEach
    void setup() {
        barbeariaService = Mockito.mock(BarbeariaService.class);
        barbeariaController = new BarbeariaController(barbeariaService);
    }

    @Test
    void deveAlterarNome() {
        Barbearia barbearia = new Barbearia();
        barbearia.setNomeBarbearia("Novo Nome");

        when(barbeariaService.alterarNomeBarbearia(any(), anyString()))
                .thenReturn(barbearia);

        ResponseEntity<Barbearia> response =
                barbeariaController.alterarNomeBarbearia(barbearia, "Antigo Nome");

        assertEquals(202, response.getStatusCode().value());
        assertEquals("Novo Nome", response.getBody().getNomeBarbearia());
    }

    @Test
    void deveAlterarHorario() {
        Barbearia barbearia = new Barbearia();

        when(barbeariaService.alterarHorariosFun(any(), any(), any()))
                .thenReturn(barbearia);

        ResponseEntity<Barbearia> response =
                barbeariaController.alterarHorariosFun(
                        barbearia,
                        LocalTime.of(8,0),
                        LocalTime.of(18,0)
                );

        assertEquals(202, response.getStatusCode().value());
    }

    @Test
    void deveAlterarTelefone() {
        Barbearia barbearia = new Barbearia();
        barbearia.setTelefoneBarbearia("11999999999");

        when(barbeariaService.alterarTelefone(any(), anyString()))
                .thenReturn(barbearia);

        ResponseEntity<Barbearia> response =
                barbeariaController.alterarTelefone(barbearia, "11999999999");

        assertEquals(202, response.getStatusCode().value());
        assertEquals("11999999999", response.getBody().getTelefoneBarbearia());
    }

    @Test
    void deveAlterarEndereco() {
        Barbearia barbearia = new Barbearia();
        barbearia.setRua("Rua Nova");

        when(barbeariaService.alterarEndereco(any(), anyString(), anyString()))
                .thenReturn(barbearia);

        ResponseEntity<Barbearia> response =
                barbeariaController.alterarEndereco(barbearia, "Rua Nova", "123");

        assertEquals(202, response.getStatusCode().value());
        assertEquals("Rua Nova", response.getBody().getRua());
    }

    @Test
    void deveAlterarProprietario() {
        Barbearia barbearia = new Barbearia();
        Proprietario proprietario = new Proprietario();

        when(barbeariaService.alterarProprietario(any(), any()))
                .thenReturn(barbearia);

        ResponseEntity<Barbearia> response =
                barbeariaController.alterarProprietario(barbearia, proprietario);

        assertEquals(202, response.getStatusCode().value());
    }
}