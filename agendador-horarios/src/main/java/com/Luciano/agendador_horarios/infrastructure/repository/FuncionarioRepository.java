package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Funcionario findByNomeFuncionario(String nomeFuncionario);

    @Transactional
    void deleteByNomeFuncionario(String nomeFuncionario);

    Funcionario findByTelefoneFuncionario(String telefoneFuncionario);

    List<Funcionario> findByIdFuncionarioAndNomeFuncionario(long id_Funcionario, String nomeFuncionario);
}
