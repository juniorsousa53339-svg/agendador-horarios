package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {




    Funcionario findByNomeFuncionarioAndTelefoneFuncionarioAndEspecialidade(
            String nomeFuncionario, String telefoneFuncionario, String especialidade);

    Optional<Funcionario> findById(UUID id);

    List<Funcionario> findByNomeFuncionarioContainingIgnoreCase(String nome);


    Funcionario findByNomeFuncionario(String nome);

    Funcionario findByTelefoneFuncionario(String telefoneFuncionario);

    @Transactional
    void deleteByNomeFuncionario(String nome);


}
