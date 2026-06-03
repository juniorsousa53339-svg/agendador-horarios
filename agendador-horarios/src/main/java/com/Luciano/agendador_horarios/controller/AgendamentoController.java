package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.DTO.AgendamentoRequestDTO;
import com.Luciano.agendador_horarios.DTO.AgendamentoResponseDTO;
import com.Luciano.agendador_horarios.infrastructure.entity.Agendamento;
import com.Luciano.agendador_horarios.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final  @Valid AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(@RequestBody AgendamentoRequestDTO dto) {
        var criado = agendamentoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@RequestParam LocalDateTime dataHora,
                                        @RequestParam UUID idCliente) {
        agendamentoService.deletar(dataHora, idCliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> listarDoDia(@RequestParam LocalDate data,
                                                         @RequestParam UUID idCliente,
                                                         @Valid AgendamentoRequestDTO dto) {

        var agendamentos = agendamentoService.buscarDoDia(data, idCliente);
        return ResponseEntity.ok(agendamentos);
    }

    @PutMapping
    public ResponseEntity<AgendamentoResponseDTO> alterar(@RequestParam LocalDateTime dataHoraAtual,
                                                          @RequestParam UUID idClienteAtual,
                                                          @RequestParam LocalDateTime dataHoraNova,
                                                          @RequestParam UUID idClienteNovo) {

        var atualizado = agendamentoService.alterar(dataHoraAtual, idClienteAtual, dataHoraNova, idClienteNovo);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/disponivel")
    public ResponseEntity<Boolean> verificarDisponibilidade(
            @RequestParam UUID idFuncionario,
            @RequestParam LocalDateTime dataHora
    ) {

        boolean disponivel =
                agendamentoService.verificarDisponibilidade(idFuncionario, dataHora);

        return ResponseEntity.ok(disponivel);
    }

    @GetMapping("/funcionarios/{id}/agendamentos")
    public ResponseEntity<List<Agendamento>> buscarAgendaFuncionario(

            @PathVariable("id") UUID idFuncionario,
            @RequestParam LocalDateTime dataHora
    ) {
        var agenda = agendamentoService
                .buscarAgendaFuncionario(idFuncionario, dataHora);

        return ResponseEntity.ok(agenda);
    }
}