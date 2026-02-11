package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {

    Barbearia findByNomeBarbeariaAndProprietario(String nomeBarbearia, Proprietario proprietario);

    @Transactional
    void deleteByNomeBarbearia(String nomeBarbearia);

    List<Barbearia> findByIdBarbeariaAndNomebarbeariaAndRuaAndnumeroRua
            (long idBarbearia, String nomeBarbearia, String rua);

    Barbearia findByNomeBarbeariaAndRuaAndNumeroRuaAndProprietario
            (String nomeBarbearia);

    Barbearia findByNomeBarbearia(String nomeBarbearia);
}

