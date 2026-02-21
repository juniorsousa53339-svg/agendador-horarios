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

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Funcionario salvarFuncionario(Funcionario funcionario) {


        Funcionario funcionarioExistente =
         funcionarioRepository.findByNomeFuncionarioAndTelefoneAndEspecialidade(

                 funcionario.getNomeFuncionario(),
                 funcionario.getTelefoneFuncionario(),
                funcionario.getEspecialidade()
         );

        if (Objects.nonNull(funcionarioExistente)) {
            throw new RuntimeException("Funcionário já está cadastrado.");
        }
        return funcionarioRepository.save(funcionario);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public void deletarFuncionario(String nomeFuncionario) {

        Funcionario funcionario =
                funcionarioRepository.findByNomeFuncionario(nomeFuncionario);

        if (Objects.isNull(funcionario)) {
            throw new RuntimeException("Funcionário não encontrado.");
        }

        funcionarioRepository.deleteByNomeFuncionario(nomeFuncionario);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Funcionario> buscarFuncionario(long idFuncionario, String nomeFuncionario) {

        List<Funcionario> funcionarios =
                funcionarioRepository.findByIdFuncionarioAndNomeFuncionario(idFuncionario, nomeFuncionario);

        if (Objects.isNull(funcionarios) || funcionarios.isEmpty()) {
            throw new RuntimeException("Funcionário não encontrado.");
        }

        return funcionarios;
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Funcionario alterarNomeFuncionario(String nomeAtual, String nomeNovo) {

        Funcionario funcionarioExistente =
                funcionarioRepository.findByNomeFuncionario(nomeAtual);

        if (Objects.isNull(funcionarioExistente)) {
            throw new RuntimeException("Funcionário não encontrado.");
        }

        funcionarioExistente.setNomeFuncionario(nomeNovo);
        return funcionarioRepository.save(funcionarioExistente);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Funcionario alterarTelefoneFuncionario(String telefoneAtual, String nomeNovo) {

        Funcionario funcionarioComTelefone =
                funcionarioRepository.findByTelefoneFuncionario(telefoneAtual);

        if (Objects.isNull(funcionarioComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        funcionarioComTelefone.setTelefoneFuncionario(nomeNovo);
        return funcionarioRepository.save(funcionarioComTelefone);
    }
}
