package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente) {

        String nomeCliente = cliente.getNomeCliente();
        Cliente clienteExistente = clienteRepository.findByNomeCliente(nomeCliente);

        if (Objects.nonNull(clienteExistente)) {
            throw new RuntimeException("Cliente já cadastrado.");
        }
        return clienteRepository.save(cliente);
    }

    public void deletarCliente(String nomeCliente) {
        clienteRepository.deleteByNomeCliente(nomeCliente);
    }

    public List<Cliente> buscarCliente(long idCliente, String nomeCliente) {
        return clienteRepository.findByIdClienteAndNomeCliente(idCliente, nomeCliente);
    }

    public Cliente alterarNomeCliente(Cliente cliente, String nomeCliente) {
        Cliente clienteExistente = clienteRepository.findByNomeCliente(nomeCliente);

        if (Objects.isNull(clienteExistente)) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        cliente.setNomeCliente(nomeCliente);
        return clienteRepository.save(cliente);
    }

    public Cliente alterarTelefoneCliente(Cliente cliente, String telefoneCliente) {
        Cliente clienteComTelefone = clienteRepository.findByTelefoneCliente(telefoneCliente);

        if (Objects.isNull(clienteComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        cliente.setTelefoneCliente(clienteComTelefone.getTelefoneCliente());
        return clienteRepository.save(cliente);
    }

    public Cliente alterarDadosCliente(Cliente cliente, String nomeCliente, String telefoneCliente) {
        Cliente clienteExistente = clienteRepository.findByNomeCliente(nomeCliente);

        if (Objects.isNull(clienteExistente)) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        cliente.setNomeCliente(nomeCliente);
        cliente.setTelefoneCliente(telefoneCliente);
        return clienteRepository.save(cliente);
    }
}
