package com.Luciano.agendador_horarios.controller;


import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.service.BarbeariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/barbearias")
@RequiredArgsConstructor
public class BarbeariaController {

    private final BarbeariaService barbeariaService;

    @PostMapping
    public ResponseEntity<Barbearia> salvarBarbearia(@RequestBody Barbearia barbearia) {
        return ResponseEntity.accepted().body(barbeariaService.salvarBarbearia(barbearia));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarBarbearia( @RequestParam String nomeBarbearia) {

        barbeariaService.deletarBarbearia(nomeBarbearia);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Barbearia>> buscarBarbearia
            (@RequestParam String nomeBarbearia, @RequestParam long idBarbearia, @RequestParam String rua) {
        return ResponseEntity.ok().body(barbeariaService.buscarBarbearia(idBarbearia,nomeBarbearia,rua));
    }

    @PutMapping("/barbearias/alterar-nome")
    public ResponseEntity<Barbearia> alterarNomeBarbearia(@RequestBody Barbearia barbearia,
                                                          @RequestParam String nomeBarbearia) {
        return ResponseEntity.accepted().body(barbeariaService.alterarNomeBarbearia(barbearia, nomeBarbearia));
    }

    @PutMapping("/barbearias/alterar-horarios")
    public ResponseEntity<Barbearia> alterarHorariosFun(@RequestBody Barbearia barbearia,
                                                        @RequestParam LocalTime horarioAbertura,
                                                        @RequestParam LocalTime horarioFechamento) {
        return ResponseEntity.accepted().body(barbeariaService.alterarHorariosFun
                (barbearia, horarioAbertura, horarioFechamento));
    }

    @PutMapping("/barbearias/alterar-telefone")
    public ResponseEntity<Barbearia> alterarTelefone(@RequestBody Barbearia barbearia,
                                                     @RequestParam String telefoneBarbearia) {

        return ResponseEntity.accepted().body(barbeariaService.alterarTelefone(barbearia, telefoneBarbearia));
    }

    @PutMapping("/barbearias/alterar-endereco")
    public ResponseEntity<Barbearia> alterarEndereco(@RequestBody Barbearia barbearia,
                                                     @RequestParam String rua,
                                                     @RequestParam String numeroRua) {
        return ResponseEntity.accepted().body(barbeariaService.alterarEndereco(barbearia, rua, numeroRua));
    }
    @PutMapping("/barbearias/alterar-proprietario")
    public ResponseEntity<Barbearia> alterarProprietario(@RequestBody Barbearia barbearia,
                                                         @RequestParam Proprietario proprietario) {
        return ResponseEntity.accepted().body(barbeariaService.alterarProprietario(barbearia,proprietario));

    }
}

 
