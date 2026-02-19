package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.infrastructure.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @PreAuthorize("hasRole('Proprietario')")
    public Funcionario salvarFuncionario(Funcionario funcionario) {

        String nomeFuncionario = funcionario.getNomeFuncionario();

        Funcionario funcionarioExistente = funcionarioRepository.findByNomeFuncionario(nomeFuncionario);

        if (Objects.nonNull(funcionarioExistente)) {
            throw new RuntimeException("Funcionário já está cadastrado.");
        }
        return funcionarioRepository.save(funcionario);
    }

    @PreAuthorize("hasRole('Proprietario')")
    public void deletarFuncionario(String nomeFuncionario) {
        funcionarioRepository.deleteByNomeFuncionario(nomeFuncionario);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Funcionario> buscarFuncionario(long idFuncionario, String nomeFuncionario) {
        return funcionarioRepository.findByIdFuncionarioAndNomeFuncionario(idFuncionario, nomeFuncionario);
    }

    @PreAuthorize("hasRole('Proprietario')")
    public Funcionario alterarNomeFuncionario(Funcionario funcionario, String nomeFuncionario) {
        Funcionario funcionarioExistente = funcionarioRepository.findByNomeFuncionario(nomeFuncionario);

        if (Objects.isNull(funcionarioExistente)) {
            throw new RuntimeException("Funcionário não encontrado.");
        }

        funcionario.setNomeFuncionario(nomeFuncionario);
        return funcionarioRepository.save(funcionario);
    }

    @PreAuthorize("hasRole('Proprietario')")
    public Funcionario alterarTelefoneFuncionario(Funcionario funcionario, String telefoneFuncionario) {
        Funcionario funcionarioComTelefone = funcionarioRepository.findByTelefoneFuncionario(telefoneFuncionario);

        if (Objects.isNull(funcionarioComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        funcionario.setTelefoneFuncionario(funcionarioComTelefone.getTelefoneFuncionario());
        return funcionarioRepository.save(funcionario);
    }
}
