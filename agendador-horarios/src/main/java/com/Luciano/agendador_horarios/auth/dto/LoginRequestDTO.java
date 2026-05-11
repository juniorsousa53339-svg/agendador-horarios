package com.Luciano.agendador_horarios.auth.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada para autenticação.
 * Mantemos este contrato enxuto para o front enviar apenas o essencial.
 */
public record LoginRequestDTO(
        @NotBlank(message = "Username é obrigatório")
        String username,

        @NotBlank(message = "Password é obrigatório")
        String password
) {
}
