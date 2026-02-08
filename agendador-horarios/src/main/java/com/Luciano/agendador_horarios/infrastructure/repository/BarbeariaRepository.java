package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {

    Barbearia findByNomeBarbearia(String nomeBarbearia);

    List<Barbearia> findById_barbeariaAndNomebarbeariaAndEndereco
            (long id_barbearia, String nomeBarbearia, String endereco);
}
