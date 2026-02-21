package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente) {

        String nomeCliente = cliente.getNomeCliente();
        Cliente clienteExistente = null;
        clienteExistente = clienteRepository.findByNomeCliente(nomeCliente);

        if (Objects.nonNull(clienteExistente)) {
            throw new RuntimeException("Cliente já cadastrado.");
        }
        return clienteRepository.save(cliente);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public void deletarCliente(String nomeCliente) {

        Cliente cliente = null;
        cliente = clienteRepository.findByNomeCliente(nomeCliente);

        if (Objects.isNull(cliente)) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        clienteRepository.deleteByNomeCliente(nomeCliente);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Cliente> buscarCliente(long idCliente, String nomeCliente) {

        List<Cliente> clientes = null;
        clientes = clienteRepository.findByIdClienteAndNomeCliente(idCliente, nomeCliente);

        if (Objects.isNull(clientes) || clientes.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        return clientes;
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Cliente alterarNomeCliente(Cliente cliente, String nomeCliente) {

        Cliente clienteExistente = null;
        clienteExistente = clienteRepository.findByNomeCliente(nomeCliente);

        if (Objects.isNull(clienteExistente)) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        cliente.setNomeCliente(nomeCliente);
        return clienteRepository.save(cliente);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Cliente alterarTelefoneCliente(Cliente cliente, String telefoneCliente) {

        Cliente clienteComTelefone = null;
        clienteComTelefone = clienteRepository.findByTelefoneCliente(telefoneCliente);

        if (Objects.isNull(clienteComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        cliente.setTelefoneCliente(clienteComTelefone.getTelefoneCliente());
        return clienteRepository.save(cliente);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Cliente alterarDadosCliente(Cliente cliente, String nomeCliente, String telefoneCliente) {

        Cliente clienteExistente = null;
        clienteExistente = clienteRepository.findByNomeCliente(nomeCliente);

        if (Objects.isNull(clienteExistente)) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        cliente.setNomeCliente(nomeCliente);
        cliente.setTelefoneCliente(telefoneCliente);
        return clienteRepository.save(cliente);
    }
}
