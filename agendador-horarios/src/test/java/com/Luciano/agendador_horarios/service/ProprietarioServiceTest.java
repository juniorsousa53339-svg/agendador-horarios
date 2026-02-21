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

        verify(proprietarioRepository, never())
                .save(any(Proprietario.class));
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

        String nomeAtual = "Carlos";
        String nomeNovo = "Marcus";

        Proprietario proprietarioMock = new Proprietario();
        proprietarioMock.setNome(nomeAtual);

        when(proprietarioRepository.findByNome(nomeAtual))
                .thenReturn(proprietarioMock);

        when(proprietarioRepository.save(any(Proprietario.class)))
                .thenReturn(proprietarioMock);

        Proprietario resultado =
                proprietarioService.alterarNome(nomeAtual, nomeNovo);

        assertEquals(nomeNovo, resultado.getNome());
        verify(proprietarioRepository).findByNome(nomeAtual);
    }

    @Test
    @DisplayName("Deve alterar telefone com sucesso")
    void deveAlterarTelefoneComSucesso() {

        String telefoneAtual = "11888888888";
        String telefoneNovo = "1177777777";

        Proprietario proprietarioMock = new Proprietario();
        proprietarioMock.setTelefone(telefoneAtual);

        when(proprietarioRepository.findByTelefone(telefoneAtual))
                .thenReturn(proprietarioMock);

        when(proprietarioRepository.save(any(Proprietario.class)))
                .thenReturn(proprietarioMock);

        Proprietario result =
                proprietarioService.alterarTelefone(telefoneAtual, telefoneNovo);

        assertEquals(telefoneNovo, result.getTelefone());

        verify(proprietarioRepository).findByTelefone(telefoneAtual);

    }

    @Test
    @DisplayName("Deve alterar email com sucesso")
    void deveAlterarEmailComSucesso() {

        String emailAtual = "atual@email.com";
        String emailNovo = "novo@email.com";


        Proprietario proprietarioMock = new Proprietario();
        proprietarioMock.setEmail(emailAtual);

        when(proprietarioRepository.findByEmail(emailAtual))
                .thenReturn(proprietarioMock);

        when(proprietarioRepository.save(any(Proprietario.class)))
                .thenReturn(proprietarioMock);

        Proprietario result =
                proprietarioService.alterarEmail(emailAtual, emailNovo);

        assertEquals(emailNovo, result.getEmail());

        verify(proprietarioRepository).findByEmail(emailAtual);
    }
}