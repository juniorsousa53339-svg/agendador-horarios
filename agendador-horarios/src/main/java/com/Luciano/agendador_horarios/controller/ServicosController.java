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

        return ResponseEntity.accepted().body
                (servicosService.salvarServico(servicos));
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
                .body(servicosService.buscarServico
                        (idServico, nomeServico, precoServico));
    }

    @PutMapping("/alterar-nome")
    public ResponseEntity<Servicos> alterarNomeServico(
                                                       @RequestParam String nomeServicoAtual,
                                                       @RequestParam String nomeServicoNovo) {

        return ResponseEntity.accepted()
                .body(servicosService.alterarNomeServico
                        (nomeServicoAtual, nomeServicoNovo));
    }

    @PutMapping("/alterar-preco")
    public ResponseEntity<Servicos> alterarPrecoServico(@RequestParam BigDecimal precoServicoAtual,
                                                        @RequestParam BigDecimal precoServicoNovo) {

        return ResponseEntity.accepted()
                .body(servicosService.alterarPrecoServico
                        (precoServicoAtual, precoServicoNovo));
    }

    @PutMapping("/alterar-descricao")
    public ResponseEntity<Servicos> alterarDescricaoServico(@RequestBody String descricaoAtual,
                                                            @RequestParam String descricaoNova) {

        return ResponseEntity.accepted()
                .body(servicosService.alterarDescricaoServico
                        (descricaoAtual, descricaoNova));
    }

    @PutMapping("/alterar-duracao")
    public ResponseEntity<Servicos> alterarDuracaoServico(@RequestParam Integer duracaoAtual,
                                                          @RequestParam Integer duracaoNova) {

        return ResponseEntity.accepted()
                .body(servicosService.alterarDuracaoServico(duracaoAtual, duracaoNova));
    }
}
