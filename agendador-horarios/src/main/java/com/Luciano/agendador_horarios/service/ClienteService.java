package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Service responsável pela gestão do cadastro de clientes.
 * Centraliza as regras de validação de dados pessoais e permissões de modificação.
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Registra um novo cliente no sistema.
     * Valida a existência prévia para evitar registros duplicados com mesmo nome e telefone.
     */
    public Cliente salvarCliente(Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findByNomeClienteAndTelefoneCliente(
                cliente.getNomeCliente(),
                cliente.getTelefoneCliente()
        );

        if (Objects.nonNull(clienteExistente)) {
            throw new RuntimeException("Cliente já cadastrado.");
        }
        return clienteRepository.save(cliente);
    }

    /**
     * Remove o registro de um cliente do banco de dados.
     * Restrito ao perfil de Proprietário por questões de segurança de dados.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public void deletarCliente(String nomeCliente) {
        Cliente cliente = clienteRepository.findByNomeCliente(nomeCliente);

        if (Objects.isNull(cliente)) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        clienteRepository.deleteByNomeCliente(nomeCliente);
    }

    /**
     * Recupera uma lista de clientes baseada em filtros de ID e Nome.
     */
    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Cliente> buscarCliente(UUID idCliente, String nomeCliente) {
        List<Cliente> clientes = clienteRepository.findByIdClienteAndNomeCliente(idCliente, nomeCliente);

        if (Objects.isNull(clientes) || clientes.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        return clientes;
    }

    /**
     * Atualiza o nome de um cliente existente após validar sua presença no sistema.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Cliente alterarNomeCliente(String atualNomeCliente, String novoNomeCliente) {
        Cliente clienteExistente = clienteRepository.findByNomeCliente(atualNomeCliente);

        if (Objects.isNull(clienteExistente)) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        clienteExistente.setNomeCliente(novoNomeCliente);
        return clienteRepository.save(clienteExistente);
    }

    /**
     * Atualiza o número de telefone de contato de um cliente.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Cliente alterarTelefoneCliente(String telefoneAtual, String TelefoneNovo) {
        Cliente clienteComTelefone = clienteRepository.findByTelefoneCliente(telefoneAtual);

        if (Objects.isNull(clienteComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        clienteComTelefone.setTelefoneCliente(TelefoneNovo);
        return clienteRepository.save(clienteComTelefone);
    }
}