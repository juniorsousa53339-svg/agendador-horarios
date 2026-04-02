package com.Luciano.agendador_horarios.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Configuração central de segurança do Spring Security.
 * Define permissões de rotas, políticas de CORS e proteção contra ataques comuns.
 */
@Configuration
@EnableMethodSecurity // Habilita o uso de @PreAuthorize nos Controllers e Services
public class SecurityConfig {

    /**
     * Define a corrente de filtros de segurança (Filter Chain).
     * É aqui que o Spring decide se uma requisição HTTP deve ser barrada ou permitida.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desabilita CSRF: Comum em APIs REST que usam JWT ou são Stateless.
                .csrf(csrf -> csrf.disable())

                // 2. Configuração de CORS: Define quais endereços (Front-end) podem acessar esta API.
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new CorsConfiguration();

                    // Permite acesso do React (3000) e Angular (4200) em ambiente de desenvolvimento.
                    config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:4200"));

                    // Libera todos os verbos HTTP necessários para o CRUD.
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

                    // Permite qualquer cabeçalho (Headers) na requisição.
                    config.setAllowedHeaders(List.of("*"));

                    // Permite o envio de cookies ou credenciais de autenticação.
                    config.setAllowCredentials(true);

                    return config;
                }))

                // 3. Regras de Autorização: No momento (MVP), todas as rotas estão liberadas.
                // Nota de Sênior: Futuramente, aqui será configurado o '.authenticated()'.
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}