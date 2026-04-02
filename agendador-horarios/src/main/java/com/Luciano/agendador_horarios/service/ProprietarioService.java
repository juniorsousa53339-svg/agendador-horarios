package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.repository.ProprietarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Service responsável pela gestão dos perfis de proprietários.
 * Controla o acesso e a manutenção dos dados dos administradores do sistema.
 */
@Service
@RequiredArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

    /**
     * Realiza o cadastro de um novo proprietário.
     * Valida se a combinação de nome e telefone já existe para evitar duplicidade.
     */
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

    /**
     * Remove um proprietário do sistema.
     * Restrito ao perfil de Proprietário para garantir o controle de acesso.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public void deletarProprietario(String nome) {
        Proprietario proprietario = proprietarioRepository.findByNome(nome);

        // Nota de Sênior: Aqui deve ser isNull para lançar erro caso NÃO encontre
        if (Objects.isNull(proprietario)){
            throw new RuntimeException("Proprietário não encontrado!");
        }

        proprietarioRepository.deleteByNome(nome);
    }

    /**
     * Busca perfis de proprietários utilizando critérios de ID, Nome e Email.
     */
    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Proprietario> buscarProprietario(UUID idProprietario, String nome, String email) {
        List<Proprietario> proprietarios = proprietarioRepository.findByIdProprietarioAndNomeAndEmail(
                idProprietario, nome, email);

        if (Objects.isNull(proprietarios) || proprietarios.isEmpty()) {
            throw new RuntimeException("Proprietário não encontrado!");
        }

        return proprietarios;
    }

    /**
     * Atualiza o nome de um proprietário cadastrado.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Proprietario alterarNome(String nomeAtual, String novoNome) {
        Proprietario proprietarioExistente = proprietarioRepository.findByNome(nomeAtual);

        if (Objects.isNull(proprietarioExistente)) {
            throw new RuntimeException("Proprietário não encontrado.");
        }

        proprietarioExistente.setNome(novoNome);
        return proprietarioRepository.save(proprietarioExistente);
    }

    /**
     * Atualiza o telefone de contato de um proprietário.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Proprietario alterarTelefone(String telefoneAtual, String telefoneNovo) {
        Proprietario proprietarioComTelefone = proprietarioRepository.findByTelefone(telefoneAtual);

        if (Objects.isNull(proprietarioComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        proprietarioComTelefone.setTelefone(telefoneNovo);
        return proprietarioRepository.save(proprietarioComTelefone);
    }

    /**
     * Altera o e-mail de um proprietário, dado fundamental para acesso e notificações.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Proprietario alterarEmail(String emailAtual, String emailNovo) {
        Proprietario proprietarioComEmail = proprietarioRepository.findByEmail(emailAtual);

        if (Objects.isNull(proprietarioComEmail)) {
            throw new RuntimeException("Email não encontrado.");
        }

        proprietarioComEmail.setEmail(emailNovo);
        return proprietarioRepository.save(proprietarioComEmail);
    }
}