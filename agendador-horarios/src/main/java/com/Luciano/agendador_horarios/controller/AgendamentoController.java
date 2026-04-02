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

/**
 * Endpoint para gestão de agendamentos.
 * Coordena as requisições externas para as operações de criação, consulta e cancelamento.
 */
@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    /**
     * Endpoint para criar um novo agendamento.
     * Retorna 201 (Created) e os dados do agendamento confirmado.
     */
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(@RequestBody @Valid AgendamentoRequestDTO dto) {
        var criado = agendamentoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    /**
     * Endpoint para cancelar um agendamento existente.
     * Retorna 204 (No Content) após a remoção bem-sucedida.
     */
    @DeleteMapping
    public ResponseEntity<Void> deletar(@RequestParam LocalDateTime dataHora,
                                        @RequestParam UUID idCliente) {
        agendamentoService.deletar(dataHora, idCliente);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para listar os agendamentos de um cliente em uma data específica.
     */
    @GetMapping
    public ResponseEntity<List<Agendamento>> listarDoDia(@RequestParam LocalDate data,
                                                         @RequestParam UUID idCliente) {
        var agendamentos = agendamentoService.buscarDoDia(data, idCliente);
        return ResponseEntity.ok(agendamentos);
    }

    /**
     * Endpoint para atualizar informações de um agendamento (como horário ou cliente).
     */
    @PutMapping
    public ResponseEntity<AgendamentoResponseDTO> alterar(@RequestParam LocalDateTime dataHoraAtual,
                                                          @RequestParam UUID idClienteAtual,
                                                          @RequestParam LocalDateTime dataHoraNova,
                                                          @RequestParam UUID idClienteNovo) {
        var atualizado = agendamentoService.alterar(dataHoraAtual, idClienteAtual, dataHoraNova, idClienteNovo);
        return ResponseEntity.ok(atualizado);
    }
}