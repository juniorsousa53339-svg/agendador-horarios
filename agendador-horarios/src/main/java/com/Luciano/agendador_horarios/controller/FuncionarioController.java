package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> salvarFuncionario(@RequestBody Funcionario funcionario) {
        return ResponseEntity.accepted().body(funcionarioService.salvarFuncionario(funcionario));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarFuncionario(@RequestParam String nomeFuncionario) {
        funcionarioService.deletarFuncionario(nomeFuncionario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> buscarFuncionario(
            @RequestParam long idFuncionario, @RequestParam String nomeFuncionario) {
        return ResponseEntity.ok().body(funcionarioService.buscarFuncionario(idFuncionario, nomeFuncionario));
    }

    @PutMapping("/alterar-nome")
    public ResponseEntity<Funcionario> alterarNomeFuncionario(@RequestBody Funcionario funcionario, @RequestParam String nomeFuncionario) {
        return ResponseEntity.accepted().body(funcionarioService.alterarNomeFuncionario(funcionario, nomeFuncionario));
    }

    @PutMapping("/alterar-telefone")
    public ResponseEntity<Funcionario> alterarTelefoneFuncionario(@RequestBody Funcionario funcionario, @RequestParam String telefoneFuncionario) {
        return ResponseEntity.accepted().body(funcionarioService.alterarTelefoneFuncionario(funcionario, telefoneFuncionario));
    }
    @PutMapping("/alterar-dados")
    public ResponseEntity<Funcionario> alterarDadosFuncionario(@RequestBody Funcionario funcionario,
                                                               @RequestParam String nomeFuncionario,
                                                               @RequestParam String telefoneFuncionario) {
        return ResponseEntity.accepted().body(
                funcionarioService.alterarDadosFuncionario(funcionario, nomeFuncionario, telefoneFuncionario)
        );
    }
}