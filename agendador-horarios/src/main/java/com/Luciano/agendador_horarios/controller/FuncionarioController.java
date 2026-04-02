package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pela gestão do quadro de funcionários.
 * Fornece endpoints para cadastro, consulta e atualização de perfis profissionais.
 */
@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    /**
     * Endpoint para cadastrar um novo funcionário.
     */
    @PostMapping
    public ResponseEntity<Funcionario> salvarFuncionario(@RequestBody Funcionario funcionario) {
        var salvo = funcionarioService.salvarFuncionario(funcionario);
        return ResponseEntity.accepted().body(salvo);
    }

    /**
     * Endpoint para remover o registro de um profissional pelo nome.
     */
    @DeleteMapping
    public ResponseEntity<Void> deletarFuncionario(@RequestParam String nomeFuncionario) {
        funcionarioService.deletarFuncionario(nomeFuncionario);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para buscar profissionais por identificador e nome.
     */
    @GetMapping
    public ResponseEntity<List<Funcionario>> buscarFuncionario(
            @RequestParam long idFuncionario,
            @RequestParam String nomeFuncionario) {

        var lista = funcionarioService.buscarFuncionario(idFuncionario, nomeFuncionario);
        return ResponseEntity.ok().body(lista);
    }

    /**
     * Endpoint específico para atualização do nome de registro do profissional.
     */
    @PutMapping("/alterar-nome")
    public ResponseEntity<Funcionario> alterarNomeFuncionario(
            @RequestParam String nomeFuncionarioAtual,
            @RequestParam String nomeFuncionarioNovo) {

        var atualizado = funcionarioService.alterarNomeFuncionario(nomeFuncionarioAtual, nomeFuncionarioNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    /**
     * Endpoint específico para atualização do telefone de contato do profissional.
     */
    @PutMapping("/alterar-telefone")
    public ResponseEntity<Funcionario> alterarTelefoneFuncionario(
            @RequestParam String telefoneAtual,
            @RequestParam String telefoneNovo) {

        var atualizado = funcionarioService.alterarTelefoneFuncionario(telefoneAtual, telefoneNovo);
        return ResponseEntity.accepted().body(atualizado);
    }
}