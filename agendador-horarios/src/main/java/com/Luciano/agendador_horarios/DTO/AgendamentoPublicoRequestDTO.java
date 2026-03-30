package com.Luciano.agendador_horarios.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoPublicoRequestDTO(
        @NotBlank String nomeCliente,
        @NotBlank String telefoneCliente,
        @NotNull Long idFuncionario,
        @NotNull Long idServico,
        @NotNull @Future LocalDateTime dataHoraAgendamento
) {
}
