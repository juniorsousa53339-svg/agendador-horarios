package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.repository.ProprietarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProprietarioServiceTest {

    @Mock
    private ProprietarioRepository proprietarioRepository;

    @InjectMocks
    private ProprietarioService proprietarioService;

    @Test
    @DisplayName("Deve salvar proprietario com sucesso")
    void deveSalvarProprietarioComSucesso() {
        Proprietario proprietario = new Proprietario();
        proprietario.setNome("João");
        proprietario.setTelefone("11999999999");

        when(proprietarioRepository.findByNomeAndTelefone(anyString(), anyString())).thenReturn(null);
        when(proprietarioRepository.save(any(Proprietario.class))).thenReturn(proprietario);

        Proprietario resultado = proprietarioService.salvarProprietario(proprietario);

        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        verify(proprietarioRepository).save(proprietario);
    }

    @Test
    @DisplayName("Deve buscar proprietario por filtros")
    void deveBuscarProprietarioComSucesso() {
        when(proprietarioRepository.findByIdProprietarioAndNomeAndEmail(anyLong(), anyString(), anyString()))
                .thenReturn(List.of(new Proprietario()));

        List<Proprietario> resultado = proprietarioService.buscarProprietario(1L, "João", "joao@email.com");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve alterar nome com sucesso")
    void deveAlterarNomeComSucesso() {
        Proprietario existente = new Proprietario();
        existente.setNome("Nome Antigo");

        when(proprietarioRepository.findByNome("Nome Antigo")).thenReturn(existente);
        when(proprietarioRepository.save(any(Proprietario.class))).thenAnswer(inv -> inv.getArgument(0));

        Proprietario resultado = proprietarioService.alterarNome("Nome Antigo", "Nome Novo");

        assertEquals("Nome Novo", resultado.getNome());
        verify(proprietarioRepository).save(existente);
    }

    @Test
    @DisplayName("Deve lançar erro ao deletar proprietario inexistente")
    void deveLancarErroAoDeletarInexistente() {
        when(proprietarioRepository.findByNome("Inexistente")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> proprietarioService.deletarProprietario("Inexistente"));
        verify(proprietarioRepository, never()).deleteByNome(anyString());
    }
}
