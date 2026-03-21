package com.Luciano.agendador_horarios.controller;


import com.Luciano.agendador_horarios.DTO.BarbeariaRequestDTO;
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
    public ResponseEntity<Barbearia> salvarBarbearia(@RequestBody BarbeariaRequestDTO request) {

        return ResponseEntity.accepted().body
                (barbeariaService.salvarBarbearia(request.barbearia(), request.proprietario()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarBarbearia(@RequestParam String nomeBarbearia) {

        barbeariaService.deletarBarbearia(nomeBarbearia);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Barbearia>> buscarBarbearia
            (
                    @RequestParam String nomeBarbearia,
                    @RequestParam long idBarbearia,
                    @RequestParam String rua
            ) {

        return ResponseEntity.ok().body
                (barbeariaService.buscarBarbearia
                        (idBarbearia, nomeBarbearia, rua));
    }

    @PutMapping("/alterar-nome")
    public ResponseEntity<Barbearia> alterarNomeBarbearia(@RequestParam String nomeBarbeariaAtual,
                                                          @RequestParam String nomeBarbeariaNovo) {

        return ResponseEntity.accepted().body
                (barbeariaService.alterarNomeBarbearia
                        (nomeBarbeariaAtual, nomeBarbeariaNovo));
    }

    @PutMapping("/alterar-horarios-funcionamento")
    public ResponseEntity<Barbearia> alterarHorariosFun
            (
                    @RequestParam LocalTime horarioAberturaAtual,
                    @RequestParam LocalTime horarioAberturaNovo,
                    @RequestParam LocalTime horarioFechamentoAtual,
                    @RequestParam LocalTime horarioFechamentoNovo

            ) {
        return ResponseEntity.accepted().body(barbeariaService.alterarHorariosFun
                (horarioAberturaAtual, horarioAberturaNovo, horarioFechamentoAtual, horarioFechamentoNovo));
    }

    @PutMapping("/alterar-telefone")
    public ResponseEntity<Barbearia> alterarTelefone(@RequestParam String telefoneBarbeariaAtual,
                                                     @RequestParam String telefoneBarbeariaNovo) {

        return ResponseEntity.accepted().body
                (barbeariaService.alterarTelefone
                        (telefoneBarbeariaAtual, telefoneBarbeariaNovo));
    }

    @PutMapping("/alterar-endereco")
    public ResponseEntity<Barbearia> alterarEndereco
            (
                    @RequestParam String ruaAtual,
                    @RequestParam String ruaNova,
                    @RequestParam int numeroRuaAtual,
                    @RequestParam String numeroRuaNovo

            ) {
        return ResponseEntity.accepted().body
                (barbeariaService.alterarEndereco
                        (
                                ruaAtual, ruaNova,
                                numeroRuaAtual, numeroRuaNovo
                        ));
    }

    @PutMapping("/alterar-proprietario")
    public ResponseEntity<Barbearia> alterarProprietario
            (
                    @RequestParam Proprietario proprietarioAtual,
                    @RequestParam Proprietario proprietarioNovo
            ) {


        return ResponseEntity.accepted().body
                (barbeariaService.alterarProprietario
                        (proprietarioAtual, proprietarioNovo));

    }
}

 
