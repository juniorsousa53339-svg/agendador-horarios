package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.infrastructure.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Service responsável pela gestão do quadro de funcionários.
 * Controla permissões de acesso, especialidades e dados de contato dos profissionais.
 */
@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    /**
     * Cadastra um novo profissional no sistema.
     * Valida a existência prévia combinando nome, telefone e especialidade para evitar duplicidade.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Funcionario salvarFuncionario(Funcionario funcionario) {
        Funcionario funcionarioExistente = funcionarioRepository
                .findByNomeFuncionarioAndTelefoneFuncionarioAndEspecialidade(
                        funcionario.getNomeFuncionario(),
                        funcionario.getTelefoneFuncionario(),
                        funcionario.getEspecialidade()
                );

        if (Objects.nonNull(funcionarioExistente)) {
            throw new RuntimeException("Funcionário já está cadastrado.");
        }
        return funcionarioRepository.save(funcionario);
    }

    /**
     * Remove um funcionário do sistema pelo seu nome.
     * Operação restrita ao Proprietário para controle de desligamentos.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public void deletarFuncionario(String nomeFuncionario) {
        Funcionario funcionario = funcionarioRepository.findByNomeFuncionario(nomeFuncionario);

        if (Objects.isNull(funcionario)) {
            throw new RuntimeException("Funcionário não encontrado.");
        }

        funcionarioRepository.deleteByNomeFuncionario(nomeFuncionario);
    }

    /**
     * Busca profissionais utilizando filtros parciais por nome.
     * O uso de 'ContainingIgnoreCase' permite flexibilidade na pesquisa.
     */
    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Funcionario> buscarFuncionario(long idFuncionario, String nomeFuncionario) {
        List<Funcionario> funcionarios = funcionarioRepository
                .findByNomeFuncionarioContainingIgnoreCase(nomeFuncionario);

        if (Objects.isNull(funcionarios) || funcionarios.isEmpty()) {
            throw new RuntimeException("Funcionário não encontrado.");
        }

        return funcionarios;
    }

    /**
     * Atualiza o nome de registro de um funcionário existente.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Funcionario alterarNomeFuncionario(String nomeFuncionarioAtual, String nomeFuncionarioNovo) {
        Funcionario funcionarioExistente = funcionarioRepository.findByNomeFuncionario(nomeFuncionarioAtual);

        if (Objects.isNull(funcionarioExistente)) {
            throw new RuntimeException("Funcionário não encontrado.");
        }

        funcionarioExistente.setNomeFuncionario(nomeFuncionarioNovo);
        return funcionarioRepository.save(funcionarioExistente);
    }

    /**
     * Atualiza o telefone de contato do profissional.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Funcionario alterarTelefoneFuncionario(String telefoneAtual, String telefoneNovo) {
        Funcionario funcionarioComTelefone = funcionarioRepository.findByTelefoneFuncionario(telefoneAtual);

        if (Objects.isNull(funcionarioComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        funcionarioComTelefone.setTelefoneFuncionario(telefoneNovo);
        return funcionarioRepository.save(funcionarioComTelefone);
    }
}