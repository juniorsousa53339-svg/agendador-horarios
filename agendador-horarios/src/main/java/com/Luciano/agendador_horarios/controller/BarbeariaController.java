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
import java.util.UUID;

@RestController
@RequestMapping("/barbearias")
@RequiredArgsConstructor
public class BarbeariaController {

    private final BarbeariaService barbeariaService;

    @PostMapping
    public ResponseEntity<Barbearia> salvarBarbearia(@RequestBody BarbeariaRequestDTO request) {
        var salvo = barbeariaService.salvarBarbearia(request.barbearia(), request.proprietario());
        return ResponseEntity.accepted().body(salvo);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarBarbearia(@RequestParam String nomeBarbearia) {
        barbeariaService.deletarBarbearia(nomeBarbearia);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Barbearia>> buscarBarbearia(
            @RequestParam  String nomeBarbearia,
            @RequestParam  UUID idBarbearia,
            @RequestParam  String rua) {

        var lista = barbeariaService.buscarBarbearia(idBarbearia, nomeBarbearia, rua);
        return ResponseEntity.ok().body(lista);
    }

    @PutMapping("/alterar-nome")
    public ResponseEntity<Barbearia> alterarNomeBarbearia(
            @RequestParam String nomeBarbeariaAtual,
            @RequestParam String nomeBarbeariaNovo) {

        var atualizado = barbeariaService.alterarNomeBarbearia(nomeBarbeariaAtual, nomeBarbeariaNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    @PutMapping("/alterar-horarios-funcionamento")
    public ResponseEntity<Barbearia> alterarHorariosFun(
            @RequestParam LocalTime horarioAberturaAtual,
            @RequestParam LocalTime horarioAberturaNovo,
            @RequestParam LocalTime horarioFechamentoAtual,
            @RequestParam LocalTime horarioFechamentoNovo) {

        var atualizado = barbeariaService.alterarHorariosFun(
                horarioAberturaAtual, horarioAberturaNovo,
                horarioFechamentoAtual, horarioFechamentoNovo);

        return ResponseEntity.accepted().body(atualizado);
    }

    @PutMapping("/alterar-telefone")
    public ResponseEntity<Barbearia> alterarTelefone(
            @RequestParam String telefoneBarbeariaAtual,
            @RequestParam String telefoneBarbeariaNovo) {

        var atualizado = barbeariaService.alterarTelefone(telefoneBarbeariaAtual, telefoneBarbeariaNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    @PutMapping("/alterar-endereco")
    public ResponseEntity<Barbearia> alterarEndereco(
            @RequestParam String ruaAtual,
            @RequestParam String ruaNova,
            @RequestParam int numeroRuaAtual,
            @RequestParam String numeroRuaNovo) {

        var atualizado = barbeariaService.alterarEndereco(ruaAtual, ruaNova, numeroRuaAtual, numeroRuaNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    @PutMapping("/alterar-proprietario")
    public ResponseEntity<Barbearia> alterarProprietario(
            @RequestParam Proprietario proprietarioAtual,
            @RequestParam Proprietario proprietarioNovo) {

        var atualizado = barbeariaService.alterarProprietario(proprietarioAtual, proprietarioNovo);
        return ResponseEntity.accepted().body(atualizado);
    }
}