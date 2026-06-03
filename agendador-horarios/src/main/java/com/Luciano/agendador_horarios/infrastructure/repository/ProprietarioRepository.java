package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, UUID> {
    Proprietario findByNomeAndTelefone(String nome, String telefone);

    @Transactional
    void deleteByNome(String nome);

    List<Proprietario> findByIdProprietarioAndNomeAndEmail( UUID idProprietario, String nome, String email);

    Proprietario findByNome(String nome);

    Proprietario findByTelefone(String telefone);

    Proprietario findByEmail(String email);
}