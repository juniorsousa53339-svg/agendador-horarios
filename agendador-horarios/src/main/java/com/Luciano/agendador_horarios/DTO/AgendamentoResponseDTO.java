package com.Luciano.agendador_horarios.DTO;

import java.time.LocalDateTime;
import java.util.UUID;


public record AgendamentoResponseDTO(

        UUID idAgendamento,
        UUID idCliente,
        String nomeCliente,


        UUID idFuncionario,
        String nomeFuncionario,


        UUID idServico,
        String nomeServico,


        LocalDateTime dataHoraAgendamento,
        String status
) {}