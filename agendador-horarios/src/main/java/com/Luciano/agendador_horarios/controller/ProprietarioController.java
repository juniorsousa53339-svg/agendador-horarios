package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.service.ProprietarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller responsável pela gestão dos perfis de proprietários.
 * Centraliza as operações de cadastro e atualização dos administradores do sistema.
 */
@RestController
@RequestMapping("/proprietarios")
@RequiredArgsConstructor
public class ProprietarioController {

    private final ProprietarioService proprietarioService;

    /**
     * Endpoint para cadastrar um novo proprietário no sistema.
     */
    @PostMapping
    public ResponseEntity<Proprietario> salvarProprietario(@RequestBody @Valid Proprietario proprietario) {
        var salvo = proprietarioService.salvarProprietario(proprietario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /**
     * Endpoint para remover o registro de um proprietário pelo nome.
     */
    @DeleteMapping
    public ResponseEntity<Void> deletarProprietario(@RequestParam String nome) {
        proprietarioService.deletarProprietario(nome);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para busca de proprietários utilizando filtros de Nome, ID e Email.
     */
    @GetMapping
    public ResponseEntity<List<Proprietario>> buscarProprietario(
            @RequestParam String nome,
            @RequestParam UUID id_proprietario,
            @RequestParam String email) {

        var lista = proprietarioService.buscarProprietario(id_proprietario, nome, email);
        return ResponseEntity.ok().body(lista);
    }

    /**
     * Endpoint específico para atualização do nome do proprietário.
     */
    @PutMapping("/alterar-nome")
    public ResponseEntity<Proprietario> alterarNome(
            @RequestParam String nomeAtual,
            @RequestParam String novoNome) {

        var atualizado = proprietarioService.alterarNome(nomeAtual, novoNome);
        return ResponseEntity.accepted().body(atualizado);
    }

    /**
     * Endpoint específico para atualização do telefone de contato.
     */
    @PutMapping("/alterar-telefone")
    public ResponseEntity<Proprietario> alterarTelefone(
            @RequestParam String telefoneAtual,
            @RequestParam String telefoneNovo) {

        var atualizado = proprietarioService.alterarTelefone(telefoneAtual, telefoneNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    /**
     * Endpoint específico para atualização do e-mail de acesso/notificação.
     */
    @PutMapping("/alterar-email")
    public ResponseEntity<Proprietario> alterarEmail(
            @RequestParam String emailAtual,
            @RequestParam String emailNovo) {

        var atualizado = proprietarioService.alterarEmail(emailAtual, emailNovo);
        return ResponseEntity.accepted().body(atualizado);
    }
}
