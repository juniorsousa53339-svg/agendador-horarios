package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.service.ProprietarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/proprietarios")
@RequiredArgsConstructor
public class ProprietarioController {

    private final ProprietarioService proprietarioService;

    @PostMapping
    public ResponseEntity<Proprietario> salvarProprietario(@RequestBody Proprietario proprietario) {
        return ResponseEntity.accepted().body(proprietarioService.salvarProprietario(proprietario));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarProprietario(@RequestParam String nome) {
        proprietarioService.deletarProprietario(nome);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Proprietario>> buscarProprietario(
            @RequestParam String nome,
            @RequestParam long idProprietario,
            @RequestParam String email
    ) {
        return ResponseEntity.ok().body(
                proprietarioService.buscarProprietario(idProprietario, nome, email)
        );
    }

    @PutMapping("/alterar-nome")
    public ResponseEntity<Proprietario> alterarNome(
            @RequestParam String nomeAtual,
            @RequestParam String novoNome
    ) {
        return ResponseEntity.accepted().body(
                proprietarioService.alterarNome(nomeAtual, novoNome)
        );
    }

    @PutMapping("/alterar-telefone")
    public ResponseEntity<Proprietario> alterarTelefone(
            @RequestParam String telefoneAtual,
            @RequestParam String telefoneNovo
    ) {
        return ResponseEntity.accepted().body(
                proprietarioService.alterarTelefone(telefoneAtual, telefoneNovo)
        );
    }

    @PutMapping("/alterar-email")
    public ResponseEntity<Proprietario> alterarEmail(
            @RequestParam String emailAtual,
            @RequestParam String emailNovo
    ) {
        return ResponseEntity.accepted().body(
                proprietarioService.alterarEmail(emailAtual, emailNovo)
        );
    }
}
