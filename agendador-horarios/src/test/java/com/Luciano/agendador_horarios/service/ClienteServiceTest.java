package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Deve salvar um cliente e retornar o objeto salvo")
    void deveSalvarClientesComSucesso() {

        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Luciano");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);


        Cliente result = clienteService.salvarCliente(cliente);


        assertNotNull(result);
        assertEquals("Luciano", result.getNomeCliente());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("Deve chamar o repositório para deletar um cliente por nome")
    void deveDeletarClienteComSucesso() {

        clienteService.deletarCliente("Luciano");


        verify(clienteRepository, times(1)).deleteByNomeCliente("Luciano");
    }

    @Test
    @DisplayName("Deve buscar cliente por ID e Nome")
    void deveBuscarClienteComSucesso() {

        clienteService.buscarCliente(2, "Dafiner");


        verify(clienteRepository).findByIdClienteAndNomeCliente(2, "Dafiner");
    }

    @Test
    @DisplayName("Deve atualizar o nome do cliente corretamente")
    void deveAlterarNomeComSucesso() {

        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Luciano");
        String novoNome = "Luciano Junior";


        when(clienteRepository.findByNomeCliente(novoNome)).thenReturn(cliente);


        clienteService.alterarNomeCliente(cliente, novoNome);


        verify(clienteRepository).findByNomeCliente(novoNome);

    }
}