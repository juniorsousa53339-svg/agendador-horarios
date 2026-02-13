package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> salvarCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.accepted().body(clienteService.salvarCliente(cliente));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarCliente(@RequestParam String nomeCliente) {
        clienteService.deletarCliente(nomeCliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> buscarCliente(
            @RequestParam long idCliente, @RequestParam String nomeCliente) {
        return ResponseEntity.ok().body(clienteService.buscarCliente(idCliente, nomeCliente));
    }

    @PutMapping("/alterar-nome")
    public ResponseEntity<Cliente> alterarNomeCliente(@RequestBody Cliente cliente, @RequestParam String nomeCliente) {
        return ResponseEntity.accepted().body(clienteService.alterarNomeCliente(cliente, nomeCliente));
    }

    @PutMapping("/alterar-telefone")
    public ResponseEntity<Cliente> alterarTelefoneCliente(@RequestBody Cliente cliente, @RequestParam String telefoneCliente) {
        return ResponseEntity.accepted().body(clienteService.alterarTelefoneCliente(cliente, telefoneCliente));
    }

    @PutMapping("/alterar-dados")
    public ResponseEntity<Cliente> alterarDadosCliente(@RequestBody Cliente cliente, @RequestParam String nomeCliente, @RequestParam String telefoneCliente) {
        return ResponseEntity.accepted().body(clienteService.alterarDadosCliente(cliente, nomeCliente, telefoneCliente));
    }
}