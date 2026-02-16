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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        proprietario.setEmail("joao@email.com");

        when(proprietarioRepository.findByNomeAndTelefone(any(), any())).thenReturn(null);
        when(proprietarioRepository.save(any(Proprietario.class))).thenReturn(proprietario);

        Proprietario resultado = proprietarioService.salvarProprietario(proprietario);

        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        verify(proprietarioRepository).save(any(Proprietario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar proprietario já existente")
    void deveLancarExcecaoAoSalvarProprietarioExistente() {

        Proprietario proprietario = new Proprietario();
        when(proprietarioRepository.findByNomeAndTelefone(any(), any())).thenReturn(proprietario);

        assertThrows(RuntimeException.class,
                () -> proprietarioService.salvarProprietario(proprietario));

        verify(proprietarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar proprietario com sucesso")
    void deveDeletarProprietarioComSucesso() {

        String nome = "João";

        proprietarioService.deletarProprietario(nome);

        verify(proprietarioRepository).deleteByNome(nome);
    }

    @Test
    @DisplayName("Deve buscar proprietario por filtros")
    void deveBuscarProprietarioComSucesso() {

        when(proprietarioRepository
                .findByIdProprietarioAndNomeAndEmail(anyLong(), anyString(), anyString()))
                .thenReturn(List.of(new Proprietario()));

        List<Proprietario> result =
                proprietarioService.buscarProprietario(1L, "João", "joao@email.com");

        assertFalse(result.isEmpty());
        verify(proprietarioRepository)
                .findByIdProprietarioAndNomeAndEmail(1L, "João", "joao@email.com");
    }

    @Test
    @DisplayName("Deve alterar nome de proprietario")
    void deveAlterarNomeProprietarioComSucesso() {

        Proprietario proprietario = new Proprietario();
        String nome = "Carlos";

        Proprietario proprietarioMock = new Proprietario();
        proprietarioMock.setNome(nome);

        when(proprietarioRepository.findByNome(nome)).thenReturn(proprietarioMock);
        when(proprietarioRepository.save(any())).thenReturn(proprietario);

        Proprietario resultado =
                proprietarioService.alterarNome(proprietario, nome);

        assertEquals(nome, resultado.getNome());
        verify(proprietarioRepository).findByNome(nome);
    }

    @Test
    @DisplayName("Deve alterar telefone com sucesso")
    void deveAlterarTelefoneComSucesso() {

        Proprietario proprietario = new Proprietario();
        String telefone = "11888888888";

        Proprietario proprietarioMock = new Proprietario();
        proprietarioMock.setTelefone(telefone);

        when(proprietarioRepository.findByTelefone(telefone)).thenReturn(proprietarioMock);
        when(proprietarioRepository.save(any())).thenReturn(proprietario);

        Proprietario result =
                proprietarioService.alterarTelefone(proprietario, telefone);

        assertEquals(telefone, result.getTelefone());
        verify(proprietarioRepository).save(proprietario);
    }

    @Test
    @DisplayName("Deve alterar email com sucesso")
    void deveAlterarEmailComSucesso() {

        Proprietario proprietario = new Proprietario();
        String email = "novo@email.com";

        Proprietario proprietarioMock = new Proprietario();
        proprietarioMock.setEmail(email);

        when(proprietarioRepository.findByEmail(email)).thenReturn(proprietarioMock);
        when(proprietarioRepository.save(any())).thenReturn(proprietario);

        Proprietario result =
                proprietarioService.alterarEmail(proprietario, email);

        assertEquals(email, result.getEmail());
        verify(proprietarioRepository).save(proprietario);
    }
}