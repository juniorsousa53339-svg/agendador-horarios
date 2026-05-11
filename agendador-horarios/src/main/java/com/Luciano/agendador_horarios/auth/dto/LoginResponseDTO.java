package com.Luciano.agendador_horarios.auth.dto;

/**
 * DTO de saída da autenticação.
 * Retornamos token JWT e metadados básicos para facilitar o consumo no front-end.
 */
public record LoginResponseDTO(
        String accessToken,
        String tokenType,
        Long expiresInSeconds,
        String role,
        String username
) {
}
