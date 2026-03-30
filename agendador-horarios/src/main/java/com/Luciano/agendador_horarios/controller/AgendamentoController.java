package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.DTO.AgendamentoRequestDTO;
import com.Luciano.agendador_horarios.DTO.AgendamentoResponseDTO;
import com.Luciano.agendador_horarios.DTO.AgendamentoPublicoRequestDTO;
import com.Luciano.agendador_horarios.infrastructure.entity.Agendamento;
import com.Luciano.agendador_horarios.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<AgendamentoResponseDTO> criar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        var criado = agendamentoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PostMapping("/publico")
    public ResponseEntity<AgendamentoResponseDTO> criarPublico(@RequestBody @Valid AgendamentoPublicoRequestDTO dto) {
        var criado = agendamentoService.criarPublico(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@RequestParam LocalDateTime dataHora,
                                        @RequestParam Long idCliente) {
        agendamentoService.deletar(dataHora, idCliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public ResponseEntity<List<Agendamento>> listarDoDia(@RequestParam LocalDate data,
                                                         @RequestParam Long idCliente) {
        return ResponseEntity.ok(agendamentoService.buscarDoDia(data, idCliente));
    }

    @GetMapping("/funcionario")
    @PreAuthorize("hasRole('FUNCIONARIO')")
    public ResponseEntity<List<Agendamento>> listarAgendaFuncionario(@RequestParam LocalDate data,
                                                                     @RequestParam Long idCliente) {
        return ResponseEntity.ok(agendamentoService.buscarDoDia(data, idCliente));
    }

    @GetMapping("/proprietario")
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public ResponseEntity<List<Agendamento>> listarAgendaProprietario(@RequestParam LocalDate data,
                                                                      @RequestParam Long idCliente) {
        return ResponseEntity.ok(agendamentoService.buscarDoDia(data, idCliente));
    }

    @PutMapping
    public ResponseEntity<AgendamentoResponseDTO> alterar(@RequestParam LocalDateTime dataHoraAtual,
                                                          @RequestParam Long idClienteAtual,
                                                          @RequestParam LocalDateTime dataHoraNova,
                                                          @RequestParam Long idClienteNovo) {
        var atualizado = agendamentoService.alterar(dataHoraAtual, idClienteAtual, dataHoraNova, idClienteNovo);
        return ResponseEntity.ok(atualizado);
    }
}
