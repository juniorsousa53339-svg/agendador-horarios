package com.Luciano.agendador_horarios.security;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordValidatorService {

    private final BCryptPasswordEncoder passwordEncoder;

    public void validarSenha(Proprietario proprietario, String senhaDigitada) {
        if (!passwordEncoder.matches(senhaDigitada, proprietario.getSenha())) {
            throw new RuntimeException("Senha inválida!");
        }
    }
}
