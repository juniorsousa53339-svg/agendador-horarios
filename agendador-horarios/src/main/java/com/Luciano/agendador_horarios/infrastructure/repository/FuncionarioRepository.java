package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    // troque "Telefone" -> "TelefoneFuncionario"
    Funcionario findByNomeFuncionarioAndTelefoneFuncionarioAndEspecialidade(
            String nomeFuncionario, String telefoneFuncionario, String especialidade);

    Optional<Funcionario> findById(Long id);

    List<Funcionario> findByNomeFuncionarioContainingIgnoreCase(String nome);
    List<Funcionario> findByIdFuncionarioAndNomeFuncionarioContainingIgnoreCase(Long idFuncionario, String nomeFuncionario);

    Funcionario findByNomeFuncionario(String nome);

    // troque "Telefone" -> "TelefoneFuncionario"
    Funcionario findByTelefoneFuncionario(String telefoneFuncionario);

    void deleteByNomeFuncionario(String nome);
}
