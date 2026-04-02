package com.Luciano.agendador_horarios.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO de Saída para confirmação de agendamentos.
 * Retorna os dados processados e legíveis para o usuário final.
 * Inclui nomes e status, além dos identificadores técnicos (UUIDs).
 */
public record AgendamentoResponseDTO(

        /** Identificador único do registro de agendamento no banco. */
        UUID idAgendamento,

        /** ID do cliente vinculado ao agendamento. */
        UUID idCliente,

        /** Nome do cliente para exibição direta na interface. */
        String nomeCliente,

        /** ID do profissional que realizará o atendimento. */
        UUID idFuncionario,

        /** Nome do profissional para facilitar a leitura no Front-end. */
        String nomeFuncionario,

        /** ID do serviço contratado (Corte, Barba, etc). */
        UUID idServico,

        /** Nome descritivo do serviço. */
        String nomeServico,

        /** Data e hora confirmadas para o serviço. */
        LocalDateTime dataHoraAgendamento,

        /** Situação atual do agendamento (Ex: CONFIRMADO, PENDENTE, CANCELADO). */
        String status
) {}