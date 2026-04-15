package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller responsável por gerenciar o ciclo de vida dos dados dos clientes.
 * Oferece endpoints para cadastro, consulta e manutenção de informações pessoais.
 */
@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Endpoint para realizar o cadastro inicial de um cliente.
     *
     * Ajuste aplicado:
     * - Uso de @Valid para validar constraints da entidade/DTO na borda da API.
     * - Retorno HTTP 201 (Created) por semântica REST em criação síncrona.
     */
    @PostMapping
    public ResponseEntity<Cliente> salvarCliente(@RequestBody @Valid Cliente cliente) {
        var salvo = clienteService.salvarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /**
     * Endpoint para remover o registro de um cliente pelo nome.
     */
    @DeleteMapping
    public ResponseEntity<Void> deletarCliente(@RequestParam String nomeCliente) {
        clienteService.deletarCliente(nomeCliente);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para busca de clientes filtrada por identificador único e nome.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> buscarCliente(
            @RequestParam UUID idCliente,
            @RequestParam String nomeCliente) {

        var lista = clienteService.buscarCliente(idCliente, nomeCliente);
        return ResponseEntity.ok().body(lista);
    }

    /**
     * Endpoint específico para a atualização do nome de registro do cliente.
     */
    @PutMapping("/alterar-nome")
    public ResponseEntity<Cliente> alterarNomeCliente(
            @RequestParam String atualNomeCliente,
            @RequestParam String novoNomeCliente) {

        var atualizado = clienteService.alterarNomeCliente(atualNomeCliente, novoNomeCliente);
        return ResponseEntity.accepted().body(atualizado);
    }

    /**
     * Endpoint específico para a atualização do telefone de contato do cliente.
     *
     * Ajuste aplicado:
     * - padronização do nome do parâmetro para camelCase (`telefoneNovo`).
     */
    @PutMapping("/alterar-telefone")
    public ResponseEntity<Cliente> alterarTelefoneCliente(
            @RequestParam String telefoneAtual,
            @RequestParam String telefoneNovo) {

        var atualizado = clienteService.alterarTelefoneCliente(telefoneAtual, telefoneNovo);
        return ResponseEntity.accepted().body(atualizado);
    }
}
