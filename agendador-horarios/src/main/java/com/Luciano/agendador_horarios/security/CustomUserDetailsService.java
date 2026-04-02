package com.Luciano.agendador_horarios.security;

import com.Luciano.agendador_horarios.infrastructure.entity.Usuario;
import com.Luciano.agendador_horarios.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service customizado para a ponte entre o Banco de Dados e o Spring Security.
 * Implementa 'UserDetailsService' para permitir que o Spring consulte as credenciais
 * durante o processo de autenticação (Login).
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Localiza o usuário no banco e converte para o formato que o Spring Security entende.
     * * @param username O identificador (e-mail ou login) enviado pelo usuário.
     * @return UserDetails Objeto contendo credenciais e permissões (Roles).
     * @throws UsernameNotFoundException Caso o usuário não conste na base de dados.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Busca segura utilizando Optional e lançamento de exceção amigável ao Spring.
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não cadastrado no Barber Pro: " + username));

        /* * 2. Construção do UserDetails (padrão Builder):
         * - username: Login do usuário.
         * - password: A senha (que deve estar criptografada via BCrypt no banco).
         * - roles: Removemos o prefixo 'ROLE_' pois o método .roles() do Builder
         * já o adiciona internamente para compatibilidade com @PreAuthorize.
         */
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRole().name().replace("ROLE_", ""))
                .build();
    }
}