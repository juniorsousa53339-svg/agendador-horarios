package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.repository.BarbeariaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BarbeariaServiceTest {

    @Mock
    private BarbeariaRepository barbeariaRepository;

    @InjectMocks
    private BarbeariaService barbeariaService;

    @Test
    void salvarBarbearia_deveLancarExcecaoQuandoJaExisteCadastro() {
        Barbearia barbearia = new Barbearia();
        Proprietario proprietario = new Proprietario();
        barbearia.setNomeBarbearia("Barber Prime");
        barbearia.setProprietario(proprietario);

        when(barbeariaRepository.findByNomeBarbeariaAndProprietario("Barber Prime", proprietario))
                .thenReturn(new Barbearia());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> barbeariaService.salvarBarbearia(barbearia));

        assertEquals("Barbearia já está cadastrada.", exception.getMessage());
        verify(barbeariaRepository, never()).save(any(Barbearia.class));
    }

    @Test
    void salvarBarbearia_deveSalvarQuandoNaoExisteCadastro() {
        Barbearia barbearia = new Barbearia();
        Proprietario proprietario = new Proprietario();
        barbearia.setNomeBarbearia("Barber Prime");
        barbearia.setProprietario(proprietario);

        when(barbeariaRepository.findByNomeBarbeariaAndProprietario("Barber Prime", proprietario)).thenReturn(null);
        when(barbeariaRepository.save(barbearia)).thenReturn(barbearia);

        Barbearia resultado = barbeariaService.salvarBarbearia(barbearia);

        assertSame(barbearia, resultado);
        verify(barbeariaRepository).save(barbearia);
    }

    @Test
    void alterarNomeBarbearia_deveAtualizarNomeQuandoEncontrarExistente() {
        Barbearia barbearia = new Barbearia();
        String nomeNovo = "Barber Center";

        when(barbeariaRepository.findByNomeBarbearia(nomeNovo)).thenReturn(new Barbearia());
        when(barbeariaRepository.save(barbearia)).thenReturn(barbearia);

        Barbearia resultado = barbeariaService.alterarNomeBarbearia(barbearia, nomeNovo);

        assertEquals(nomeNovo, barbearia.getNomeBarbearia());
        assertSame(barbearia, resultado);
    }

    @Test
    void alterarHorariosFun_deveAtualizarHorarioComDadosEncontrados() {
        Barbearia entrada = new Barbearia();
        LocalTime abertura = LocalTime.of(8, 0);
        LocalTime fechamento = LocalTime.of(18, 0);

        Barbearia encontrado = new Barbearia();
        encontrado.setHorarioAbertura(abertura);
        encontrado.setHorarioFechamento(fechamento);

        when(barbeariaRepository.findByHorarioAberturaAndHorarioFechamento(abertura, fechamento)).thenReturn(encontrado);
        when(barbeariaRepository.save(entrada)).thenReturn(entrada);

        Barbearia resultado = barbeariaService.alterarHorariosFun(entrada, abertura, fechamento);

        assertEquals(abertura, entrada.getHorarioAbertura());
        assertEquals(fechamento, entrada.getHorarioFechamento());
        assertSame(entrada, resultado);
    }
}
