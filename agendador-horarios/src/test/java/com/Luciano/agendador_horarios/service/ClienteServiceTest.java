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

    @Test
    @DisplayName("Deve alterar telefone com sucesso")
    void deveAlterarTelefoneComSucesso() {
        Cliente cliente = new Cliente();
        String telefone = "11999999999";

        Cliente clienteMock = new Cliente();
        clienteMock.setTelefoneCliente(telefone);

        when(clienteRepository.findByTelefoneCliente(telefone)).thenReturn(clienteMock);
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente result = clienteService.alterarTelefoneCliente(cliente, telefone);

        assertEquals(telefone, result.getTelefoneCliente());
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Deve alterar os dados do cliente com sucesso")
    void deveAlterarDadosClienteComSucesso() {


        Cliente clienteExistente = new Cliente();
        clienteExistente.setNomeCliente("Dafiner");
        clienteExistente.setTelefoneCliente("11 98888777");


        when(clienteRepository.findByNomeCliente("Dafiner")).thenReturn(clienteExistente);


        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNomeCliente("Dafiner");
        clienteAtualizado.setTelefoneCliente("11 99999999");


        Cliente resultado = clienteService.alterarDadosCliente(clienteAtualizado, "Dafiner", "11 99999999");


        assertEquals("Dafiner", resultado.getNomeCliente());
        assertEquals("11 99999999", resultado.getTelefoneCliente());


        verify(clienteRepository, times(1)).save(clienteAtualizado);
    }
}