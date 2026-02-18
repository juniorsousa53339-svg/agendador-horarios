package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.repository.ProprietarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private void validarSenha(Proprietario proprietario, String senhaDigitada) {
        if (!passwordEncoder.matches(senhaDigitada, proprietario.getSenha())) {
            throw new RuntimeException("Senha inválida!");
        }
    }

    public Proprietario salvarProprietario(Proprietario proprietario) {

        String senhaCodificada = passwordEncoder.encode(proprietario.getSenha());
        passwordEncoder.encode(proprietario.getSenha());


        String nome = proprietario.getNome();
        String telefone = proprietario.getTelefone();

        Proprietario proprietarioExistente = proprietarioRepository.findByNomeAndTelefone(nome, telefone);

        if (Objects.nonNull(proprietarioExistente)) {
            throw new RuntimeException("Proprietário já está cadastrado.");
        }

        return proprietarioRepository.save(proprietario);
    }

    public void deletarProprietario(String nome, String senhaDigitada) {

        Proprietario proprietario = proprietarioRepository.findByNome(nome);

        validarSenha(proprietario, senhaDigitada);

        proprietarioRepository.deleteByNome(nome);
    }

    public List<Proprietario> buscarProprietario(long idProprietario, String nome, String email, String senhaDigitada) {

        Proprietario proprietario = proprietarioRepository.findByNome(nome);

        if (proprietario == null) {
            throw new RuntimeException("Proprietário não encontrado!");
        }

        validarSenha(proprietario, senhaDigitada);

        return proprietarioRepository.findByIdProprietarioAndNomeAndEmail(idProprietario, nome, email);
    }

    public Proprietario alterarNome(Proprietario proprietario, String nome, String senhaDigitada) {

        Proprietario proprietarioExistente = proprietarioRepository.findByNome(nome);

        if (Objects.isNull(proprietarioExistente)) {
            throw new RuntimeException("Proprietário não encontrado.");
        }

        validarSenha(proprietarioExistente, senhaDigitada);

        proprietario.setNome(proprietarioExistente.getNome());

        return proprietarioRepository.save(proprietario);
    }

    public Proprietario alterarTelefone(Proprietario proprietario, String telefone, String senhaDigitada) {

        Proprietario proprietarioComTelefone = proprietarioRepository.findByTelefone(telefone);

        if (Objects.isNull(proprietarioComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        validarSenha(proprietarioComTelefone, senhaDigitada);

        proprietario.setTelefone(proprietarioComTelefone.getTelefone());

        return proprietarioRepository.save(proprietario);
    }

    public Proprietario alterarEmail(Proprietario proprietario, String email, String senhaDigitada) {

        Proprietario proprietarioComEmail = proprietarioRepository.findByEmail(email);

        if (Objects.isNull(proprietarioComEmail)) {
            throw new RuntimeException("Email não encontrado.");
        }

        validarSenha(proprietarioComEmail, senhaDigitada);

        proprietario.setEmail(proprietarioComEmail.getEmail());

        return proprietarioRepository.save(proprietario);
    }
}