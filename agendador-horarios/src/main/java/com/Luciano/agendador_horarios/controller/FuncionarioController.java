package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.DTO.AlterarFuncionarioRequest;
import com.Luciano.agendador_horarios.DTO.BuscarFuncionarioResponse;
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

    @DeleteMapping("/{nomeFuncionario}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable String nomeFuncionario) {
        funcionarioService.deletarFuncionario(nomeFuncionario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<Funcionario>buscarFuncionario(@PathVariable UUID idFuncionario) {

        Funcionario funcionario =
                funcionarioService.buscarFuncionario(idFuncionario);

        return  ResponseEntity.ok().body(funcionario);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Funcionario>> listarFuncionario() {

        var lista = funcionarioService.listarTodos();

        return ResponseEntity.ok().body(lista);
    }


    @PutMapping("/{idFuncionario}")
    public ResponseEntity<Void> alterarDadosFuncionario(

            @PathVariable  UUID idFuncionario,
            @RequestBody @Valid AlterarFuncionarioRequest req
    ){

        funcionarioService.alterarDados(
                idFuncionario,
                req.getNomeFuncionario(),
                req.getTelefoneFuncionario(),
                req.getEspecialidade(),
                req.getEmail(),
                req.getSenha()
        );

        return ResponseEntity.ok().build();
    }
}