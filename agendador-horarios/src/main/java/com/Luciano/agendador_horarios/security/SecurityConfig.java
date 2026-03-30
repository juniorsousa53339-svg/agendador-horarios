package com.Luciano.agendador_horarios.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**").disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var c = new CorsConfiguration();
                    c.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:4200")); // React + Angular local
                    c.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
                    c.setAllowedHeaders(List.of("*"));
                    c.setAllowCredentials(true);
                    return c;
                }))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .httpBasic(basic -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/h2-console/**",
                                "/clientes/publico",
                                "/funcionarios/publico",
                                "/servicos/publico",
                                "/agendamentos/publico"
                        ).permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }
}
