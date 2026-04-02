package com.Luciano.agendador_horarios.DTO;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import jakarta.validation.constraints.NotNull;

/**
 * DTO Composto para a criação de uma nova unidade de Barbearia.
 * Permite que o cadastro da empresa e do seu respectivo dono seja enviado em uma única requisição.
 * O uso de 'record' garante a imutabilidade dos dados durante o transporte.
 */
public record BarbeariaRequestDTO(

        /** Objeto contendo os dados estruturais da barbearia (Nome, Endereço, Horários). */
        @NotNull(message = "Os dados da barbearia são obrigatórios")
        Barbearia barbearia,

        /** Objeto contendo as informações do proprietário responsável pela unidade. */
        @NotNull(message = "Os dados do proprietário são obrigatórios")
        Proprietario proprietario
) {}