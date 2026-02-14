package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.controller.ClienteController;
import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class ClienteAppicationTests {

    private ClienteService clienteService;
    private ClienteController clienteController;
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setup() {
        clienteRepository = Mockito.mock(ClienteRepository.class);
        clienteService = new ClienteService(clienteRepository);
        clienteController = new ClienteController(clienteService);
    }

    @Test
    void deveSalvarClienteComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Luciano");
        cliente.setTelefoneCliente("11999999999");

        when(clienteRepository.save(cliente)).thenReturn(cliente);
        Cliente result = clienteService.salvarCliente(cliente);
        assertEquals("Luciano", result.getNomeCliente());

    }
}
