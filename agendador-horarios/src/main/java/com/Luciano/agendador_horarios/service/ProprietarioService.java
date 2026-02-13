package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.repository.ProprietarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

    public Proprietario salvarProprietario(Proprietario proprietario) {

        String nome = proprietario.getNome();
        String telefone = proprietario.getTelefone();

        Proprietario proprietarioExistente = proprietarioRepository.findByNomeAndTelefone(nome, telefone);

        if (Objects.nonNull(proprietarioExistente)) {
            throw new RuntimeException("Proprietário já está cadastrado.");
        }

        return proprietarioRepository.save(proprietario);
    }

    public void deletarProprietario(String nome) {
        proprietarioRepository.deleteByNome(nome);
    }

    public List<Proprietario> buscarProprietario(long idProprietario, String nome, String email) {
        return proprietarioRepository.findByIdProprietarioAndNomeAndEmail(idProprietario, nome, email);
    }

    public Proprietario alterarNome(Proprietario proprietario, String nome) {

        Proprietario proprietarioExistente = proprietarioRepository.findByNome(nome);

        if (Objects.isNull(proprietarioExistente)) {
            throw new RuntimeException("Proprietário não encontrado.");
        }

        proprietario.setNome(proprietarioExistente.getNome());
        return proprietarioRepository.save(proprietario);
    }

    public Proprietario alterarTelefone(Proprietario proprietario, String telefone) {

        Proprietario proprietarioComTelefone = proprietarioRepository.findByTelefone(telefone);

        if (Objects.isNull(proprietarioComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        proprietario.setTelefone(proprietarioComTelefone.getTelefone());
        return proprietarioRepository.save(proprietario);
    }

    public Proprietario alterarEmail(Proprietario proprietario, String email) {

        Proprietario proprietarioComEmail = proprietarioRepository.findByEmail(email);

        if (Objects.isNull(proprietarioComEmail)) {
            throw new RuntimeException("Email não encontrado.");
        }

        proprietario.setEmail(proprietarioComEmail.getEmail());
        return proprietarioRepository.save(proprietario);
    }
}