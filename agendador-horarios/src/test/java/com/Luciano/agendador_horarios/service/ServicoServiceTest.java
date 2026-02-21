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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicosServiceTest {

    @Mock
    private ServicosRepository servicosRepository;

    @InjectMocks
    private ServicosService servicosService;

    @Test
    @DisplayName("Deve salvar serviço com sucesso")
    void deveSalvarServicoComSucesso() {
        Servicos servico = new Servicos();
        servico.setNomeServico("Corte");
        servico.setDescricaoServico("Corte masculino");

        when(servicosRepository.findByNomeServicoAndDescricaoServico("Corte", "Corte masculino"))
                .thenReturn(null);
        when(servicosRepository.save(any(Servicos.class))).thenReturn(servico);

        Servicos resultado = servicosService.salvarServico(servico);

        assertNotNull(resultado);
        assertEquals("Corte", resultado.getNomeServico());
    }

    @Test
    @DisplayName("Deve deletar serviço existente")
    void deveDeletarServicoComSucesso() {
        Servicos servico = new Servicos();
        servico.setNomeServico("Corte");

        when(servicosRepository.findByNomeServico("Corte")).thenReturn(servico);

        servicosService.deletarServico("Corte");

        verify(servicosRepository, times(1)).deleteByNomeServico("Corte");
    }

    @Test
    @DisplayName("Deve buscar serviço por filtros")
    void deveBuscarServicoComSucesso() {
        when(servicosRepository.findByIdServicoAndNomeServicoAndPrecoServico(1L, "Corte", new BigDecimal("50.00")))
                .thenReturn(List.of(new Servicos()));

        var resultado = servicosService.buscarServico(1L, "Corte", new BigDecimal("50.00"));

        assertEquals(1, resultado.size());
    }
}
