package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@Repository
public interface BarbeariaRepository extends JpaRepository<Barbearia, UUID> {

    Barbearia findByNomeBarbeariaAndProprietario(String nomeBarbearia, Proprietario proprietario);

    @Transactional
    void deleteByNomeBarbearia(String nomeBarbearia);


    List<Barbearia> findByIdBarbeariaAndNomeBarbeariaAndRua(UUID idBarbearia, String nomeBarbearia, String rua);

    Barbearia findByNomeBarbearia(String nomeBarbearia);

    Barbearia findByHorarioAberturaAndHorarioFechamento(LocalTime horarioAbertura, LocalTime horarioFechamento);

    Barbearia findByTelefoneBarbearia(String telefoneBarbearia);

    Barbearia findByRuaAndNumeroRua(String rua, int numeroRua);

    Barbearia findByProprietario(Proprietario proprietario);

}