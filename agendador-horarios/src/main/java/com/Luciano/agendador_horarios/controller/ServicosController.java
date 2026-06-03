package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import com.Luciano.agendador_horarios.service.ServicosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicosController {

    private final ServicosService servicosService;

    @PostMapping
    public ResponseEntity<Servicos> salvarServico(@RequestBody @Valid Servicos servicos) {
        var salvo = servicosService.salvarServico(servicos);
        return ResponseEntity.accepted().body(salvo);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarServico(@RequestParam String nomeServico) {
        servicosService.deletarServico(nomeServico);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Servicos>> buscarServico(
            @RequestParam UUID idServico,
            @RequestParam String nomeServico,
            @RequestParam BigDecimal precoServico) {

        var lista = servicosService.buscarServico(idServico, nomeServico, precoServico);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Servicos>> listarServicos() {

        var lista = servicosService.listarTodos();

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/alterar-nome")
    public ResponseEntity<Servicos> alterarNomeServico(
            @RequestParam String nomeServicoAtual,
            @RequestParam String nomeServicoNovo) {

        var atualizado = servicosService.alterarNomeServico(nomeServicoAtual, nomeServicoNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    @PutMapping("/alterar-preco")
    public ResponseEntity<Servicos> alterarPrecoServico(
            @RequestParam BigDecimal precoServicoAtual,
            @RequestParam BigDecimal precoServicoNovo) {

        var atualizado = servicosService.alterarPrecoServico(precoServicoAtual, precoServicoNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    @PutMapping("/alterar-descricao")
    public ResponseEntity<Servicos> alterarDescricaoServico(
            @RequestBody String descricaoAtual,
            @RequestParam String descricaoNova) {

        var atualizado = servicosService.alterarDescricaoServico(descricaoAtual, descricaoNova);
        return ResponseEntity.accepted().body(atualizado);
    }

    @PutMapping("/alterar-duracao")
    public ResponseEntity<Servicos> alterarDuracaoServico(
            @RequestParam Integer duracaoAtual,
            @RequestParam Integer duracaoNova) {

        var atualizado = servicosService.alterarDuracaoServico(duracaoAtual, duracaoNova);
        return ResponseEntity.accepted().body(atualizado);
    }
}