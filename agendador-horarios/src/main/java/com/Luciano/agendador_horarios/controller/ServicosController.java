package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import com.Luciano.agendador_horarios.service.ServicosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Controller responsável por gerenciar as operações do catálogo de serviços.
 * Oferece endpoints para manutenção de preços, durações e descrições das atividades.
 */
@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicosController {

    private final ServicosService servicosService;

    /**
     * Endpoint para cadastrar um novo serviço no catálogo.
     */
    @PostMapping
    public ResponseEntity<Servicos> salvarServico(@RequestBody Servicos servicos) {
        var salvo = servicosService.salvarServico(servicos);
        return ResponseEntity.accepted().body(salvo);
    }

    /**
     * Endpoint para remover um serviço do catálogo pelo nome comercial.
     */
    @DeleteMapping
    public ResponseEntity<Void> deletarServico(@RequestParam String nomeServico) {
        servicosService.deletarServico(nomeServico);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para busca de serviços utilizando filtros de ID, Nome e Preço.
     */
    @GetMapping
    public ResponseEntity<List<Servicos>> buscarServico(
            @RequestParam UUID idServico,
            @RequestParam String nomeServico,
            @RequestParam BigDecimal precoServico) {

        var lista = servicosService.buscarServico(idServico, nomeServico, precoServico);
        return ResponseEntity.ok().body(lista);
    }

    /**
     * Endpoint específico para a atualização do nome do serviço.
     */
    @PutMapping("/alterar-nome")
    public ResponseEntity<Servicos> alterarNomeServico(
            @RequestParam String nomeServicoAtual,
            @RequestParam String nomeServicoNovo) {

        var atualizado = servicosService.alterarNomeServico(nomeServicoAtual, nomeServicoNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    /**
     * Endpoint específico para o reajuste de preço de um serviço.
     */
    @PutMapping("/alterar-preco")
    public ResponseEntity<Servicos> alterarPrecoServico(
            @RequestParam BigDecimal precoServicoAtual,
            @RequestParam BigDecimal precoServicoNovo) {

        var atualizado = servicosService.alterarPrecoServico(precoServicoAtual, precoServicoNovo);
        return ResponseEntity.accepted().body(atualizado);
    }

    /**
     * Endpoint específico para atualização da descrição detalhada do serviço.
     */
    @PutMapping("/alterar-descricao")
    public ResponseEntity<Servicos> alterarDescricaoServico(
            @RequestBody String descricaoAtual,
            @RequestParam String descricaoNova) {

        var atualizado = servicosService.alterarDescricaoServico(descricaoAtual, descricaoNova);
        return ResponseEntity.accepted().body(atualizado);
    }

    /**
     * Endpoint específico para ajuste do tempo estimado de duração do serviço.
     */
    @PutMapping("/alterar-duracao")
    public ResponseEntity<Servicos> alterarDuracaoServico(
            @RequestParam Integer duracaoAtual,
            @RequestParam Integer duracaoNova) {

        var atualizado = servicosService.alterarDuracaoServico(duracaoAtual, duracaoNova);
        return ResponseEntity.accepted().body(atualizado);
    }
}