package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import com.Luciano.agendador_horarios.service.ServicosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicosController {

    private final ServicosService servicosService;

    @PostMapping
    public ResponseEntity<Servicos> salvarServico(@RequestBody Servicos servicos) {
        return ResponseEntity.accepted().body(servicosService.salvarServico(servicos));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarServico(@RequestParam String nomeServico) {

        servicosService.deletarServico(nomeServico);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Servicos>> buscarServico(
            @RequestParam long idServico,
            @RequestParam String nomeServico,
            @RequestParam BigDecimal precoServico) {

        return ResponseEntity.ok()
                .body(servicosService.buscarServico(idServico, nomeServico, precoServico));
    }

    @PutMapping("/alterar-nome")
    public ResponseEntity<Servicos> alterarNomeServico(@RequestBody Servicos servicos,
                                                       @RequestParam String nomeServico) {

        return ResponseEntity.accepted()
                .body(servicosService.alterarNomeServico(servicos, nomeServico));
    }

    @PutMapping("/alterar-preco")
    public ResponseEntity<Servicos> alterarPrecoServico(@RequestBody Servicos servicos,
                                                        @RequestParam BigDecimal precoServico) {

        return ResponseEntity.accepted()
                .body(servicosService.alterarPrecoServico(servicos, precoServico));
    }

    @PutMapping("/alterar-descricao")
    public ResponseEntity<Servicos> alterarDescricaoServico(@RequestBody Servicos servicos,
                                                            @RequestParam String descricaoServico) {

        return ResponseEntity.accepted()
                .body(servicosService.alterarDescricaoServico(servicos, descricaoServico));
    }
}