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


        Cliente clienteExistente =
        clienteRepository.findByNomeCliente(
                cliente.getNomeCliente());

        if (Objects.nonNull(clienteExistente)) {
            throw new RuntimeException("Cliente já cadastrado.");
        }
        return clienteRepository.save(cliente);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public void deletarCliente(String nomeCliente) {

        Cliente cliente =
         clienteRepository.findByNomeCliente(nomeCliente);

        if (Objects.isNull(cliente)) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        clienteRepository.deleteByNomeCliente(nomeCliente);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Cliente> buscarCliente(long idCliente, String nomeCliente) {

        List<Cliente> clientes =
         clienteRepository.findByIdClienteAndNomeCliente(idCliente, nomeCliente);

        if (Objects.isNull(clientes) || clientes.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        return clientes;
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Cliente alterarNomeCliente(String atualNomeCliente,
                                      String novoNomeCliente) {

        Cliente clienteExistente =
         clienteRepository.findByNomeCliente(atualNomeCliente);

        if (Objects.isNull(clienteExistente)) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        clienteExistente.setNomeCliente(novoNomeCliente);
        return clienteRepository.save(clienteExistente);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Cliente alterarTelefoneCliente(String telefoneAtual, String TelefoneNovo) {

        Cliente clienteComTelefone =
        clienteRepository.findByTelefoneCliente(telefoneAtual);

        if (Objects.isNull(clienteComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        clienteComTelefone.setTelefoneCliente(TelefoneNovo);
        return clienteRepository.save(clienteComTelefone);
    }
}
