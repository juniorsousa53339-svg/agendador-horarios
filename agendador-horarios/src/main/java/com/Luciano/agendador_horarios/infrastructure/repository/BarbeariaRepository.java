package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {

    Barbearia findByNomeBarbeariaAndRuaAndNumeroRua(String nomeBarbearia, String rua, int numeroRua);

    @Transactional
    void deleteByNomeBarbearia(String nomeBarbearia);

    List<Barbearia> findByNomeBarbearia(String nomeBarbearia);
}
