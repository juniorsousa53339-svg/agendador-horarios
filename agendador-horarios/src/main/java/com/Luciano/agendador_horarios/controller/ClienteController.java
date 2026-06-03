package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> salvarCliente(@RequestBody @Valid Cliente cliente) {
        var salvo = clienteService.salvarCliente(cliente);
        return ResponseEntity.accepted().body(salvo);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarCliente(@RequestParam String nomeCliente) {
        clienteService.deletarCliente(nomeCliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> buscarCliente(
            @RequestParam UUID idCliente,
            @RequestParam String nomeCliente) {

        var lista = clienteService.buscarCliente(idCliente, nomeCliente);
        return ResponseEntity.ok().body(lista);
    }

    @PutMapping("/alterar-nome")
    public ResponseEntity<Cliente> alterarNomeCliente(
            @RequestParam String atualNomeCliente,
            @RequestParam String novoNomeCliente) {

        var atualizado = clienteService.alterarNomeCliente(atualNomeCliente, novoNomeCliente);
        return ResponseEntity.accepted().body(atualizado);
    }

    @PutMapping("/alterar-telefone")
    public ResponseEntity<Cliente> alterarTelefoneCliente(
            @RequestParam String telefoneAtual,
            @RequestParam String TelefoneNovo) {

        var atualizado = clienteService.alterarTelefoneCliente(telefoneAtual, TelefoneNovo);
        return ResponseEntity.accepted().body(atualizado);
    }
}