package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;


public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {

    Barbearia findByNomeBarbeariaAndProprietario(String nomeBarbearia, Proprietario proprietario);

    @Transactional
    void deleteByNomeBarbearia(String nomeBarbearia);

    List<Barbearia> findByIdBarbeariaAndNomeBarbeariaAndRua(long idBarbearia, String nomeBarbearia, String rua);

    Barbearia findByNomeBarbearia(String nomeBarbearia);

    Barbearia findByHorarioAberturaAndHorarioFechamento(LocalTime horarioAbertura, LocalTime horarioFechamento);

    Barbearia findByTelefoneBarbearia(String telefoneBarbearia);

    Barbearia findByRuaAndNumeroRua(String rua, String numeroRua);

    Barbearia findByProprietario(Proprietario proprietario);
}