package com.Luciano.agendador_horarios.DTO;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import jakarta.validation.constraints.NotNull;

public record BarbeariaRequestDTO(
        @NotNull Barbearia barbearia,
        @NotNull Proprietario proprietario
) {}
