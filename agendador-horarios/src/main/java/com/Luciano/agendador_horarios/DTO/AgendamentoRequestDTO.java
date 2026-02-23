package com.Luciano.agendador_horarios.DTO;


import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AgendamentoRequestDTO(
        @NotNull Long idCliente,
        @NotNull Long idFuncionario,
        @NotNull Long idServico,
        @NotNull LocalDateTime dataHoraAgendamento
) {}
