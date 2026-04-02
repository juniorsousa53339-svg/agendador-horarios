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

/**
 * Controller responsável pela gestão das unidades de barbearia.
 * Centraliza as operações de configuração do estabelecimento e seus vínculos.
 */
@RestController
@RequestMapping("/barbearias")
@RequiredArgsConstructor
public class BarbeariaController {

    private final BarbeariaService barbeariaService;

    /**
     * Endpoint para cadastrar uma nova barbearia vinculada a um proprietário.
     */
    @PostMapping
    public ResponseEntity<Barbearia> salvarBarbearia(@RequestBody BarbeariaRequestDTO request) {
        var salvo = barbeariaService.salvarBarbearia(request.barbearia(), request.proprietario());
        return ResponseEntity.accepted().body(salvo);
    }

    /**
     * Endpoint para remover uma barbearia pelo nome comercial.
     */
    @DeleteMapping
    public ResponseEntity<Void> deletarBarbearia(@RequestParam String nomeBarbearia) {
        barbeariaService.deletarBarbearia(nomeBarbearia);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para busca filtrada de barbearias por nome, ID e rua.
     */
    @GetMapping
    public ResponseEntity<List<Barbearia>> buscarBarbearia(
            @RequestParam String nomeBarbearia,
            @RequestParam UUID idBarbearia,
            @RequestParam String rua) {

        var lista = barbeariaService.buscarBarbearia(idBarbearia, nomeBarbearia, rua);
        return ResponseEntity.ok().body(lista);
    }

    /**
     * Endpoint para atualização do nome comercial da unidade.
     */
    @PutMapping("/alterar-nome")
    public ResponseEntity<Barbearia> alterarNomeBarbearia(
            @RequestParam String nomeBarbeariaAtual,
            @RequestParam String nomeBarbeariaNovo) {

        var atualizado = barbeariaService.alterarNomeBarbearia(nomeBarbeariaAtual, nomeBarbeariaNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    /**
     * Endpoint para modificação dos horários de abertura e fechamento.
     */
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

    /**
     * Endpoint para atualização do telefone de contato comercial.
     */
    @PutMapping("/alterar-telefone")
    public ResponseEntity<Barbearia> alterarTelefone(
            @RequestParam String telefoneBarbeariaAtual,
            @RequestParam String telefoneBarbeariaNovo) {

        var atualizado = barbeariaService.alterarTelefone(telefoneBarbeariaAtual, telefoneBarbeariaNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    /**
     * Endpoint para atualização do endereço físico da barbearia.
     */
    @PutMapping("/alterar-endereco")
    public ResponseEntity<Barbearia> alterarEndereco(
            @RequestParam String ruaAtual,
            @RequestParam String ruaNova,
            @RequestParam int numeroRuaAtual,
            @RequestParam String numeroRuaNovo) {

        var atualizado = barbeariaService.alterarEndereco(ruaAtual, ruaNova, numeroRuaAtual, numeroRuaNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    /**
     * Endpoint para transferência de responsabilidade ou alteração do proprietário.
     */
    @PutMapping("/alterar-proprietario")
    public ResponseEntity<Barbearia> alterarProprietario(
            @RequestParam Proprietario proprietarioAtual,
            @RequestParam Proprietario proprietarioNovo) {

        var atualizado = barbeariaService.alterarProprietario(proprietarioAtual, proprietarioNovo);
        return ResponseEntity.accepted().body(atualizado);
    }
}