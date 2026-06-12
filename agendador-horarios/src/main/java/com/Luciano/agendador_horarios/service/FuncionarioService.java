package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.infrastructure.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.naming.factory.SendMailFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

//    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Funcionario salvarFuncionario(Funcionario funcionario) {

        Funcionario funcionarioExistente = funcionarioRepository
                .findByNomeFuncionarioAndTelefoneFuncionarioAndEspecialidadeAndEmailAndSenha(

                        funcionario.getNomeFuncionario(),
                        funcionario.getTelefoneFuncionario(),
                        funcionario.getEspecialidade(),
                        funcionario.getEmail(),
                        funcionario.getSenha()
                );

        if (Objects.nonNull(funcionarioExistente)) {
            throw new RuntimeException("Funcionário já está cadastrado.");
        }
        return funcionarioRepository.
                save(funcionario);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public void deletarFuncionario(String nomeFuncionario) {

        Funcionario funcionario =
                funcionarioRepository.
                        findByNomeFuncionario
                                (nomeFuncionario);

        if (Objects.isNull(funcionario)) {
            throw new RuntimeException("Funcionário não encontrado.");
        }

        funcionarioRepository.
                deleteByNomeFuncionario
                        (nomeFuncionario);
    }

   // @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public Funcionario buscarFuncionario(UUID idFuncionario) {

        Funcionario funcionarioBuscado
                =funcionarioRepository
                .findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException
                ("Funcionario não encontrado"));

        return funcionarioBuscado;
    }

    //@PreAuthorize("hasRole('PROPRIETARIO')")
    public Funcionario alterarDados(

            UUID idFuncionario,
            String nomeFuncionario,
            String telefoneFuncionario,
            String especialidade,
            String email

    ){
        Funcionario funcionarioComDados =
                funcionarioRepository.
                        findById(idFuncionario)
                          .orElseThrow(()
                                  -> new RuntimeException
                                 ("Funcionario não encontrado")
                        );

        funcionarioComDados.alterarDados(
                nomeFuncionario,
                telefoneFuncionario,
                especialidade,
                email
        );

        return funcionarioRepository.
                save(funcionarioComDados);
    }

    //@PreAuthorize("hasRole('PROPRIETARIO')")
    public List<Funcionario> listarTodos() {return funcionarioRepository.findAll();}
}