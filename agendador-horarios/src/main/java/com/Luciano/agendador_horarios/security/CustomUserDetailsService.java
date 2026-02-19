package com.Luciano.agendador_horarios.security;


import com.Luciano.agendador_horarios.infrastructure.entity.Usuario;
import com.Luciano.agendador_horarios.infrastructure.repository.UsuarioRepository;
import com.Luciano.agendador_horarios.infrastructure.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService  implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        
        Usuario usuario = usuarioRepository.findByUsername(username)
                
                .orElseThrow(() -> 
                        new UsernameNotFoundException("Usuario no encontrado"));
        
        
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRole().name().replace("ROLE_", ""))
                .build();
    }
}
