package com.Luciano.agendador_horarios;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.repository.BarbeariaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BarbeariaServiceTest {

    @Autowired
    private BarbeariaRepository repository;

    @Test
    void deveSalvarBarbearia() {
        Barbearia b = new Barbearia();
        b.setNomeBarbearia("Teste");

        Barbearia salva = repository.save(b);

        assertNotNull(salva.getIdBarbearia());
    }

    @Test
    void deveBuscarBarbearia() {
        Barbearia barbearia = new Barbearia();
        barbearia.setNomeBarbearia("teste2");

        Barbearia salva = repository.save(barbearia);
        assertNotNull(salva.getIdBarbearia());

    }

}