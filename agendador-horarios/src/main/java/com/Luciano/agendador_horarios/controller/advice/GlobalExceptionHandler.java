package com.Luciano.agendador_horarios.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Classe base para padronizar erros HTTP da API.
 *
 * COMO USAR:
 * 1) Deixe essa classe ativa no projeto.
 * 2) Quando criar exceções específicas (ex.: RecursoNaoEncontradoException),
 *    adicione novos métodos com @ExceptionHandler para cada tipo.
 * 3) O front-end passa a receber sempre o mesmo formato JSON de erro,
 *    facilitando exibição de mensagens na interface.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handler de fallback para regras de negócio simples
     * (hoje seu código lança bastante RuntimeException).
     *
     * Dica de evolução:
     * - Trocar RuntimeException por exceções próprias e
     *   devolver status mais específico (404, 409, 422...).
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Regra de negócio inválida",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handler genérico para erros inesperados.
     *
     * Dica de evolução:
     * - Em produção, evitar expor detalhes internos da exceção.
     * - Logar stacktrace com logger (SLF4J) para análise.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * DTO interno de resposta de erro.
     *
     * Exemplo de JSON retornado:
     * {
     *   "timestamp": "2026-01-01T10:00:00",
     *   "status": 400,
     *   "error": "Regra de negócio inválida",
     *   "message": "Cliente não encontrado.",
     *   "path": "/clientes"
     * }
     */
    public record ApiErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message,
            String path
    ) {
    }
}
