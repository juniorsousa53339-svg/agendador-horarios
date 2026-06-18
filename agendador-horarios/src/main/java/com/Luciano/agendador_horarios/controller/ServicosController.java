package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.DTO.AlterarServicosResponse;
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

    @DeleteMapping("/{nomeServico}")
    public ResponseEntity<Void> deletarServico(@PathVariable String nomeServico) {
        servicosService.deletarServico(nomeServico);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idServico}")
    public ResponseEntity<Servicos> buscarServico(
            @PathVariable UUID idServico) {

        Servicos servicos =
                servicosService.buscarServico(idServico);

        return ResponseEntity.ok().body(servicos);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Servicos>> listarServicos() {

        var lista = servicosService.listarTodos();

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{idServico}")
    public ResponseEntity<Void> atualizarServico(

            @PathVariable UUID idServico,
            @RequestBody @Valid AlterarServicosResponse r
    ) {

        servicosService.alterarDados(

                idServico,
                r.getNomeServico(),
                r.getDescricaoServico(),
                r.getPrecoServico(),
                r.getDuracaoMinutos()
        );

        return ResponseEntity.accepted().build();
    }
}