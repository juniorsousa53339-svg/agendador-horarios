package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.repository.BarbeariaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BarbeariaServiceTest {

    @Mock
    private BarbeariaRepository barbeariaRepository;

    @InjectMocks
    private BarbeariaService barbeariaService;

    @Test
    @DisplayName("Deve salvar barbearia com sucesso")
    void deveSalvarBarbeariaComSucesso() {
        Barbearia barbearia = new Barbearia();
        barbearia.setNomeBarbearia("Barba & Arte");

        when(barbeariaRepository.findByNomeBarbeariaAndProprietario(any(), any())).thenReturn(null);
        when(barbeariaRepository.save(any(Barbearia.class))).thenReturn(barbearia);

        Barbearia resultado = barbeariaService.salvarBarbearia(barbearia);

        assertNotNull(resultado);
        assertEquals("Barba & Arte", resultado.getNomeBarbearia());
    }

    @Test
    @DisplayName("Deve buscar barbearias por filtros")
    void deveBuscarBarbeariaComSucesso() {
        when(barbeariaRepository.findByIdBarbeariaAndNomeBarbeariaAndRua(1L, "Nome", "Rua"))
                .thenReturn(List.of(new Barbearia()));

        var resultado = barbeariaService.buscarBarbearia(1L, "Nome", "Rua");

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve alterar proprietário com sucesso")
    void deveAlterarProprietarioComSucesso() {
        Barbearia entrada = new Barbearia();
        Proprietario proprietario = new Proprietario();

        Barbearia existente = new Barbearia();
        existente.setProprietario(proprietario);

        when(barbeariaRepository.findByProprietario(proprietario)).thenReturn(existente);
        when(barbeariaRepository.save(any(Barbearia.class))).thenReturn(entrada);

        Barbearia resultado = barbeariaService.alterarProprietario(entrada, proprietario);

        assertNotNull(resultado);
        verify(barbeariaRepository, times(1)).save(entrada);
    }
}
