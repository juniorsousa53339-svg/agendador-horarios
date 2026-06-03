package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> salvarFuncionario(@RequestBody @Valid Funcionario funcionario) {
        var salvo = funcionarioService.salvarFuncionario(funcionario);
        return ResponseEntity.accepted().body(salvo);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarFuncionario(@RequestParam String nomeFuncionario) {
        funcionarioService.deletarFuncionario(nomeFuncionario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> buscarFuncionario(
            @RequestParam (required = false) UUID idFuncionario,
            @RequestParam (required = false) String nomeFuncionario) {

        var lista = funcionarioService.buscarFuncionario(idFuncionario, nomeFuncionario);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Funcionario>> listarFuncionario() {

        var lista = funcionarioService.listarTodos();

        return ResponseEntity.ok().body(lista);
    }

    @PutMapping("/alterar-nome")
    public ResponseEntity<Funcionario> alterarNomeFuncionario(
            @RequestParam String nomeFuncionarioAtual,
            @RequestParam String nomeFuncionarioNovo) {

        var atualizado = funcionarioService.alterarNomeFuncionario(nomeFuncionarioAtual, nomeFuncionarioNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    @PutMapping("/alterar-telefone")
    public ResponseEntity<Funcionario> alterarTelefoneFuncionario(
            @RequestParam String telefoneAtual,
            @RequestParam String telefoneNovo) {

        var atualizado = funcionarioService.alterarTelefoneFuncionario(telefoneAtual, telefoneNovo);
        return ResponseEntity.accepted().body(atualizado);
    }
}