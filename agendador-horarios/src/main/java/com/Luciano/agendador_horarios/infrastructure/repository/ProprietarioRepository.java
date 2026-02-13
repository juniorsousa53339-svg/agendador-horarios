package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {

    Proprietario findByNomeAndTelefone(String nome, String telefone);

    @Transactional
    void deleteByNome(String nome);

    List<Proprietario> findByIdProprietarioAndNomeAndEmail(long idProprietario, String nome, String email);

    Proprietario findByNome(String nome);

    Proprietario findByTelefone(String telefone);

    Proprietario findByEmail(String email);
}