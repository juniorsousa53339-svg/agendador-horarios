package com.Luciano.agendador_horarios.DTO;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;


public record AgendamentoRequestDTO(

        @NotNull(message = "O ID do cliente é obrigatório")
        UUID idCliente,

        @NotNull(message = "O ID do funcionário é obrigatório")
        UUID idFuncionario,

        @NotNull(message = "O ID do serviço é obrigatório")
        UUID idServico,

        @NotNull(message = "A data e hora do agendamento são obrigatórias")
        LocalDateTime dataHoraAgendamento
) {}