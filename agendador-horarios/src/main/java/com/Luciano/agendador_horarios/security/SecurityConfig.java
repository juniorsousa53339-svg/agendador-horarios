package com.Luciano.agendador_horarios.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity

public class SecurityConfig {

    @Bean
    SecurityFilterChain
    springSecurityFilterChainTest(org.springframework.security.config.annotation.web.builders.
                                          HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.cors(withDefaults())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/auth").authenticated()

                        .requestMatchers(
                                "/proprietarios/**",
                                "/barbearias/**",
                                "/servicos/**"
                        )
                        .hasRole("PROPRIETARIO")

                        .requestMatchers("/funcionarios/**")
                        .hasAnyRole("PROPRIETARIO",
                                "FUNCIONARIO")

                        .requestMatchers("/agendamentos/**",
                                "/clientes/**")
                        .authenticated()

                        .anyRequest().authenticated()

                )
                .httpBasic(withDefaults());

        http.headers(headers ->
                headers.
                        frameOptions(
                                frame -> frame.disable()
                        ));


        return http.build();

    }


    @Bean
    UserDetailsService userDetailsServices(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("proprietarioSistema@")
                .password(encoder.encode("26121"))
                .roles("PROPRIETARIO")
                .build();

        UserDetails solicitante = User.withUsername("funcionarioluc")
                .password(encoder.encode("2026"))
                .roles("FUNCIONARIO")
                .build();

        return new InMemoryUserDetailsManager(admin, solicitante);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

