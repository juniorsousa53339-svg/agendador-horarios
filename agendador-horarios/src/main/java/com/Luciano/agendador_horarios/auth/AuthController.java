package com.Luciano.agendador_horarios.auth;

import com.Luciano.agendador_horarios.auth.dto.LoginRequestDTO;
import com.Luciano.agendador_horarios.auth.dto.LoginResponseDTO;
import com.Luciano.agendador_horarios.infrastructure.entity.Usuario;
import com.Luciano.agendador_horarios.infrastructure.repository.UsuarioRepository;
import com.Luciano.agendador_horarios.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Controller responsável pelo login da API.
 *
 * Nota de mentoria:
 * - Este endpoint valida credenciais com o AuthenticationManager do Spring Security.
 * - Se autenticado, gera um JWT assinado para uso nas demais rotas protegidas.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    /**
     * Realiza autenticação e retorna access token.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {

        // 1) Delegamos a validação de usuário/senha ao mecanismo oficial do Spring Security.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        // 2) Recuperamos o usuário autenticado para enriquecer claims e resposta.
        User principal = (User) authentication.getPrincipal();

        Usuario usuario = usuarioRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "Usuário autenticado não encontrado"));

        // 3) Geração do JWT com role no payload para permitir autorização por perfil no backend.
        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRole().name());

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                "Bearer",
                jwtService.getExpirationSeconds(),
                usuario.getRole().name(),
                usuario.getUsername()
        );

        return ResponseEntity.ok(response);
    }
}
