package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.infrastructure.entity.Agendamento;
import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<Agendamento> salvarAgendamento(@RequestBody Agendamento agendamento) {

        return ResponseEntity.accepted().body
                (agendamentoService.salvarAgendamento(agendamento));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAgendamento(@RequestBody Cliente cliente,
                                                   @RequestParam LocalDateTime dataHoraAgendamento) {

        agendamentoService.deletarAgendamento
                (dataHoraAgendamento, cliente);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> buscarAgendamentosDia(@RequestParam LocalDate data,
                                                                   @RequestBody Cliente cliente) {

        return ResponseEntity.ok().body
                (agendamentoService.buscarAgendamentosDia
                        (data, cliente));
    }

    @PutMapping("/agendamento/alterar-agendamento")
    public ResponseEntity<Agendamento> alterarAgendamentos

            (
                    @RequestBody Agendamento agendamento,
                    @RequestBody Cliente clienteAtual,
                    @RequestBody Cliente clienteNovo,
                    @RequestParam LocalDateTime dataHoraAgendamentoAtual,
                    @RequestParam LocalDateTime dataHoraAgendamentoNovo
            ) {

        return ResponseEntity.accepted().body
                (agendamentoService.alterarAgendamento
                        (
                                agendamento,
                                clienteAtual,
                                clienteNovo,
                                dataHoraAgendamentoAtual,
                                dataHoraAgendamentoNovo
                        )
                );
    }
}

