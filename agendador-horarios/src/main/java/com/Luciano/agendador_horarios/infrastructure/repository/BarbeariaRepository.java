package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    Barbearia findybyBarbeariaAndHorarioAberturaAndHorarioFechamento
            (Barbearia barbearia , LocalDateTime horarioAbertura, LocalDateTime horarioFechamento);

    Barbearia findybyBarbeariaAndHorarioAberturaAndHorarioFechamento
            (Barbearia barbearia, LocalTime horarioAbertura, LocalTime horarioFechamento);

    Barbearia findybyBarbeariaAndTelefoneBarbearia(Barbearia barbearia, String telefoneBarbearia);

    Barbearia findybyBarbeariaAndRuaAndNumeroRua(Barbearia barbearia, String rua, String numeroRua);
}


