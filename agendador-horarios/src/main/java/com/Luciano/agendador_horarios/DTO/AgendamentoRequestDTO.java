package com.Luciano.agendador_horarios.DTO;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) de entrada para novos agendamentos.
 * Representa o contrato de dados que o cliente (Front-end) deve enviar para a API.
 * O uso de 'record' garante imutabilidade e concisão.
 */
public record AgendamentoRequestDTO(

        /** Identificador único do cliente que está agendando. */
        @NotNull(message = "O ID do cliente é obrigatório")
        UUID idCliente,

        /** Identificador único do funcionário que realizará o serviço. */
        @NotNull(message = "O ID do funcionário é obrigatório")
        UUID idFuncionario,

        /** Identificador do serviço solicitado (ex: Corte, Barba). */
        @NotNull(message = "O ID do serviço é obrigatório")
        UUID idServico,

        /** Data e hora exata reservada para o atendimento. */
        @NotNull(message = "A data e hora do agendamento são obrigatórias")
        LocalDateTime dataHoraAgendamento
) {}