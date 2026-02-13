package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.service.ProprietarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Proprietario>> buscarProprietario
            (@RequestParam String nome,
             @RequestParam long id_proprietario,
             @RequestParam String email) {

        return ResponseEntity.ok()
                .body(proprietarioService.buscarProprietario(id_proprietario, nome, email));
    }

    @PutMapping("/proprietarios/alterar-nome")
    public ResponseEntity<Proprietario> alterarNome(@RequestBody Proprietario proprietario,
                                                    @RequestParam String nome) {

        return ResponseEntity.accepted()
                .body(proprietarioService.alterarNome(proprietario, nome));
    }

    @PutMapping("/proprietarios/alterar-telefone")
    public ResponseEntity<Proprietario> alterarTelefone(@RequestBody Proprietario proprietario,
                                                        @RequestParam String telefone) {

        return ResponseEntity.accepted()
                .body(proprietarioService.alterarTelefone(proprietario, telefone));
    }

    @PutMapping("/proprietarios/alterar-email")
    public ResponseEntity<Proprietario> alterarEmail(@RequestBody Proprietario proprietario,
                                                     @RequestParam String email) {

        return ResponseEntity.accepted()
                .body(proprietarioService.alterarEmail(proprietario, email));
    }
}