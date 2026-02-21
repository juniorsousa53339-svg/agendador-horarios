package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.repository.ProprietarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;
    public Proprietario salvarProprietario(Proprietario proprietario) {

        Proprietario proprietarioExistente = proprietarioRepository.findByNomeAndTelefone(
                proprietario.getNome(),
                proprietario.getTelefone()
        );

        if (Objects.nonNull(proprietarioExistente)) {
            throw new RuntimeException("Proprietário já está cadastrado.");
        }

        return proprietarioRepository.save(proprietario);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public void deletarProprietario(String nome) {

        Proprietario proprietario = proprietarioRepository.findByNome(nome);

        if (Objects.isNull(proprietario)) {
            throw new RuntimeException("Proprietário não encontrado!");
        }

        proprietarioRepository.deleteByNome(nome);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Proprietario> buscarProprietario(
            long idProprietario,
            String nome,
            String email
    ) {

        List<Proprietario> proprietarios =
                proprietarioRepository.findByIdProprietarioAndNomeAndEmail(idProprietario, nome, email);

        if (Objects.isNull(proprietarios) || proprietarios.isEmpty()) {
            throw new RuntimeException("Proprietário não encontrado!");
        }

        return proprietarios;
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Proprietario alterarNome(String nomeAtual,
                                    String novoNome) {

        Proprietario proprietarioExistente = proprietarioRepository.findByNome(nomeAtual);

        if (Objects.isNull(proprietarioExistente)) {
            throw new RuntimeException("Proprietário não encontrado.");
        }

        proprietarioExistente.setNome(novoNome);
        return proprietarioRepository.save(proprietarioExistente);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Proprietario alterarTelefone(
            String telefoneAtual,
            String telefoneNovo
    ) {

        Proprietario proprietarioComTelefone = proprietarioRepository.findByTelefone(telefoneAtual);

        if (Objects.isNull(proprietarioComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        proprietarioComTelefone.setTelefone(telefoneNovo);
        return proprietarioRepository.save(proprietarioComTelefone);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Proprietario alterarEmail(
            String emailAtual,
            String emailNovo
    ) {

        Proprietario proprietarioComEmail = proprietarioRepository.findByEmail(emailAtual);

        if (Objects.isNull(proprietarioComEmail)) {
            throw new RuntimeException("Email não encontrado.");
        }

        proprietarioComEmail.setEmail(emailNovo);
        return proprietarioRepository.save(proprietarioComEmail);
    }
}
