package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarbeariaRepository extends JpaRepository<Barbearia, Integer> {

    Barbearia findById_barbeariaAndNome(String nomeBarbearia, long id_barbearia);
}
