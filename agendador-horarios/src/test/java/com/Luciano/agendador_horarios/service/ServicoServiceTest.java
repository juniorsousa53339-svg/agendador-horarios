package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import com.Luciano.agendador_horarios.infrastructure.repository.ServicosRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicosServiceTest {

    @Mock
    private ServicosRepository servicosRepository;

    @InjectMocks
    private ServicosService servicosService;

    @Test
    @DisplayName("Deve salvar servico com sucesso")
    void deveSalvarServicoComSucesso() {

        Servicos servicos = new Servicos();
        servicos.setNomeServico("Corte");
        servicos.setDescricaoServico("Corte masculino");
        servicos.setPrecoServico(new BigDecimal("50.00"));

        when(servicosRepository
                .findByNomeServicoAndDescricaoServico(any(), any()))
                .thenReturn(null);

        when(servicosRepository.save(any(Servicos.class)))
                .thenReturn(servicos);

        Servicos resultado = servicosService.salvarServico(servicos);

        assertNotNull(resultado);
        assertEquals("Corte", resultado.getNomeServico());
        verify(servicosRepository).save(any(Servicos.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar servico já existente")
    void deveLancarExcecaoAoSalvarServicoExistente() {

        Servicos servicos = new Servicos();

        when(servicosRepository
                .findByNomeServicoAndDescricaoServico(any(), any()))
                .thenReturn(servicos);

        assertThrows(RuntimeException.class,
                () -> servicosService.salvarServico(servicos));

        verify(servicosRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar servico com sucesso")
    void deveDeletarServicoComSucesso() {

        String nomeServico = "Corte";

        servicosService.deletarServico(nomeServico);

        verify(servicosRepository).deleteByNomeServico(nomeServico);
    }

    @Test
    @DisplayName("Deve buscar servico por filtros")
    void deveBuscarServicoComSucesso() {

        when(servicosRepository
                .findByIdServicoAndNomeServicoAndPrecoServico(
                        anyLong(), anyString(), any(BigDecimal.class)))
                .thenReturn(List.of(new Servicos()));

        List<Servicos> result =
                servicosService.buscarServico(1L, "Corte", new BigDecimal("50.00"));

        assertFalse(result.isEmpty());
        verify(servicosRepository)
                .findByIdServicoAndNomeServicoAndPrecoServico(
                        1L, "Corte", new BigDecimal("50.00"));
    }

    @Test
    @DisplayName("Deve alterar nome do servico")
    void deveAlterarNomeServicoComSucesso() {

        Servicos servicos = new Servicos();
        String nomeServico = "Barba";

        Servicos servicoMock = new Servicos();
        servicoMock.setNomeServico(nomeServico);

        when(servicosRepository.findByNomeServico(nomeServico))
                .thenReturn(servicoMock);

        when(servicosRepository.save(any()))
                .thenReturn(servicos);

        Servicos resultado =
                servicosService.alterarNomeServico(servicos, nomeServico);

        assertEquals(nomeServico, resultado.getNomeServico());
        verify(servicosRepository).findByNomeServico(nomeServico);
    }

    @Test
    @DisplayName("Deve alterar preco do servico")
    void deveAlterarPrecoServicoComSucesso() {

        Servicos servicos = new Servicos();
        BigDecimal preco = new BigDecimal("80.00");

        Servicos servicoMock = new Servicos();
        servicoMock.setPrecoServico(preco);

        when(servicosRepository.findByPrecoServico(preco))
                .thenReturn(servicoMock);

        when(servicosRepository.save(any()))
                .thenReturn(servicos);

        Servicos result =
                servicosService.alterarPrecoServico(servicos, preco);

        assertEquals(preco, result.getPrecoServico());
        verify(servicosRepository).save(servicos);
    }

    @Test
    @DisplayName("Deve alterar descricao do servico")
    void deveAlterarDescricaoServicoComSucesso() {

        Servicos servicos = new Servicos();
        servicos.setNomeServico("Corte");

        String descricao = "Corte premium";

        Servicos servicoMock = new Servicos();
        servicoMock.setDescricaoServico(descricao);

        when(servicosRepository
                .findByNomeServicoAndDescricaoServico("Corte", descricao))
                .thenReturn(servicoMock);

        when(servicosRepository.save(any()))
                .thenReturn(servicos);

        Servicos result =
                servicosService.alterarDescricaoServico(servicos, descricao);

        assertEquals(descricao, result.getDescricaoServico());
        verify(servicosRepository).save(servicos);
    }
}
