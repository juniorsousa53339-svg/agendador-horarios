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

        return ResponseEntity.accepted()
                .body(clienteService.salvarCliente(cliente));
    }

    @PostMapping("/publico")
    public ResponseEntity<Cliente> salvarClientePublico(@RequestBody Cliente cliente) {
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

        return ResponseEntity.ok().body
                (clienteService.buscarCliente(idCliente, nomeCliente));
    }

    @PutMapping("/alterar-nome")
    public ResponseEntity<Cliente> alterarNomeCliente( @RequestParam String atualNomeCliente,
                                                       @RequestParam String novoNomeCliente)
    {
        return ResponseEntity.accepted().body
                (clienteService.alterarNomeCliente
                        (atualNomeCliente, novoNomeCliente));
    }

    @PutMapping("/alterar-telefone")
    public ResponseEntity<Cliente> alterarTelefoneCliente
            (
                    @RequestParam String telefoneAtual,
                    @RequestParam String telefoneNovo

            ) {


        return ResponseEntity.accepted().body
                (clienteService.alterarTelefoneCliente
                        (telefoneAtual, telefoneNovo));
    }

}
