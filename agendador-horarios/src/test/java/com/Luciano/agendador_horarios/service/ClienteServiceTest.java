package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Deve salvar cliente com sucesso")
    void deveSalvarClienteComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Luciano");

        when(clienteRepository.findByNomeCliente("Luciano")).thenReturn(null);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.salvarCliente(cliente);

        assertNotNull(result);
        assertEquals("Luciano", result.getNomeCliente());
    }

    @Test
    @DisplayName("Deve deletar cliente existente")
    void deveDeletarClienteComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Luciano");

        when(clienteRepository.findByNomeCliente("Luciano")).thenReturn(cliente);

        clienteService.deletarCliente("Luciano");

        verify(clienteRepository, times(1)).deleteByNomeCliente("Luciano");
    }

    @Test
    @DisplayName("Deve buscar cliente por id e nome")
    void deveBuscarClienteComSucesso() {
        when(clienteRepository.findByIdClienteAndNomeCliente(2, "Dafiner"))
                .thenReturn(List.of(new Cliente()));

        var resultado = clienteService.buscarCliente(2, "Dafiner");

        assertEquals(1, resultado.size());
    }
}
