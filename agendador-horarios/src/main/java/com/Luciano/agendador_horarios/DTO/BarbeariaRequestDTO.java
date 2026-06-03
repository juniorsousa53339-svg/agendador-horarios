package com.Luciano.agendador_horarios.DTO;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import jakarta.validation.constraints.NotNull;

public record BarbeariaRequestDTO(

        @NotNull(message = "Os dados da barbearia são obrigatórios")
        Barbearia barbearia,

        @NotNull(message = "Os dados do proprietário são obrigatórios")
        Proprietario proprietario
) {}