package com.Luciano.agendador_horarios.DTO;


import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long idAgendamento,
        Long idCliente,
        String nomeCliente,
        Long idFuncionario,
        String nomeFuncionario,
        Long idServico,
        String nomeServico,
        LocalDateTime dataHoraAgendamento,
        String status
) {}
