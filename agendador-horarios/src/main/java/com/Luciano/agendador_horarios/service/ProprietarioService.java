package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.repository.ProprietarioRepository;
import com.Luciano.agendador_horarios.security.PasswordValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;
    private final PasswordValidatorService passwordValidatorService;
    private final BCryptPasswordEncoder passwordEncoder;

    public Proprietario salvarProprietario(Proprietario proprietario) {

        Proprietario proprietarioExistente =
                proprietarioRepository.findByNomeAndTelefone(
                        proprietario.getNome(),
                        proprietario.getTelefone()
                );

        if (Objects.nonNull(proprietarioExistente)) {
            throw new RuntimeException("Proprietário já está cadastrado.");
        }

        String senhaCodificada = passwordEncoder.encode
                (proprietario.getSenha());

        proprietario.setSenha(senhaCodificada);


        return proprietarioRepository.save(proprietario);
    }

    public void deletarProprietario(String nome, String senhaDigitada) {

        Proprietario proprietario = proprietarioRepository.findByNome(nome);

        if (proprietario == null) {
            throw new RuntimeException("Proprietário não encontrado!");
        }

        passwordValidatorService.validarSenha(proprietario, senhaDigitada);

        proprietarioRepository.deleteByNome(nome);
    }

    public List<Proprietario> buscarProprietario(long idProprietario,
                                                 String nome,
                                                 String email,
                                                 String senhaDigitada
    ) {

        Proprietario proprietario =
                proprietarioRepository.findByNome(nome);

        if (proprietario == null) {
            throw new RuntimeException("Proprietário não encontrado!");
        }

        passwordValidatorService.validarSenha(
                proprietario, senhaDigitada
        );

        return proprietarioRepository.findByIdProprietarioAndNomeAndEmail
                (idProprietario, nome, email);
    }

    public Proprietario alterarNome(String nomeAtual,
                                    String novoNome,
                                    String senhaDigitada,
                                    Proprietario proprietario
    ) {

        Proprietario proprietarioExistente =
                proprietarioRepository.findByNome(nomeAtual);

        if (Objects.isNull(proprietarioExistente)) {
            throw new RuntimeException("Proprietário não encontrado.");
        }

        passwordValidatorService.validarSenha
                (proprietario, senhaDigitada);

        proprietarioExistente.setNome
                (novoNome);

        return proprietarioRepository.save
                (proprietarioExistente);
    }

    public Proprietario alterarTelefone(
            String telefoneAtual,
            String senhaDigitada,
            String telefoneNovo,
            Proprietario proprietario
    ) {

        Proprietario proprietarioComTelefone =
                proprietarioRepository.findByTelefone
                        (telefoneAtual);

        if (Objects.isNull(proprietarioComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        passwordValidatorService.validarSenha
                (proprietario, senhaDigitada);

        proprietarioComTelefone.setTelefone
                (telefoneNovo);

        return proprietarioRepository.save
                (proprietarioComTelefone);
    }

    public Proprietario alterarEmail(
            String emailAtual,
            String senhaDigitada,
            String emailNovo,
            Proprietario proprietario
    ) {

        Proprietario proprietarioComEmail =
                proprietarioRepository.
                        findByEmail(emailAtual);

        if (Objects.isNull(proprietarioComEmail)) {
            throw new RuntimeException("Email não encontrado.");
        }

        passwordValidatorService.validarSenha
                (proprietario, senhaDigitada);

        return proprietarioRepository.save
                (proprietarioComEmail);
    }
}