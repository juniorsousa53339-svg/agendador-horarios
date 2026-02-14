package com.Luciano.agendador_horarios;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.repository.BarbeariaRepository;
import com.Luciano.agendador_horarios.service.BarbeariaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
        verify(barbeariaRepository).save(barbearia);
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar barbearia já existente")
    void deveLancarExcecaoAoSalvarBarbeariaExistente() {
        Barbearia barbearia = new Barbearia();
        when(barbeariaRepository.findByNomeBarbeariaAndProprietario(any(), any())).thenReturn(barbearia);

        assertThrows(RuntimeException.class, () -> barbeariaService.salvarBarbearia(barbearia));
        verify(barbeariaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar barbearia com sucesso")
    void deveDeletarBarbeariaComSucesso() {
        String nome = "Barbearia do Luciano";

        barbeariaService.deletarBarbearia(nome);

        verify(barbeariaRepository).deleteByNomeBarbearia(nome);
    }

    @Test
    @DisplayName("Deve buscar barbearias por filtros")
    void deveBuscarBarbeariaComSucesso() {
        when(barbeariaRepository.findByIdBarbeariaAndNomeBarbeariaAndRua(anyLong(), anyString(), anyString()))
                .thenReturn(List.of(new Barbearia()));

        List<Barbearia> resultado = barbeariaService.buscarBarbearia(1L, "Nome", "Rua");

        assertFalse(resultado.isEmpty());
        verify(barbeariaRepository).findByIdBarbeariaAndNomeBarbeariaAndRua(1L, "Nome", "Rua");
    }

    @Test
    @DisplayName("Deve alterar nome da barbearia")
    void deveAlterarNomeBarbeariaComSucesso() {
        Barbearia barbearia = new Barbearia();
        String novoNome = "Novo Nome";

        when(barbeariaRepository.findByNomeBarbearia(novoNome)).thenReturn(barbearia);
        when(barbeariaRepository.save(any())).thenReturn(barbearia);

        Barbearia resultado = barbeariaService.alterarNomeBarbearia(barbearia, novoNome);

        assertEquals(novoNome, resultado.getNomeBarbearia());
        verify(barbeariaRepository).save(barbearia);
    }

    @Test
    @DisplayName("Deve alterar horários com sucesso")
    void deveAlterarHorariosComSucesso() {
        Barbearia barbearia = new Barbearia();
        LocalTime abertura = LocalTime.of(9, 0);
        LocalTime fechamento = LocalTime.of(18, 0);

        Barbearia barbeariaMock = new Barbearia();
        barbeariaMock.setHorarioAbertura(abertura);
        barbeariaMock.setHorarioFechamento(fechamento);

        when(barbeariaRepository.findByHorarioAberturaAndHorarioFechamento(abertura, fechamento))
                .thenReturn(barbeariaMock);
        when(barbeariaRepository.save(any())).thenReturn(barbearia);

        Barbearia resultado = barbeariaService.alterarHorariosFun(barbearia, abertura, fechamento);

        assertNotNull(resultado);
        verify(barbeariaRepository).save(barbearia);
    }

    @Test
    @DisplayName("Deve alterar telefone com sucesso")
    void deveAlterarTelefoneComSucesso() {
        Barbearia barbearia = new Barbearia();
        String telefone = "11999999999";

        Barbearia barbeariaMock = new Barbearia();
        barbeariaMock.setTelefoneBarbearia(telefone);

        when(barbeariaRepository.findByTelefoneBarbearia(telefone)).thenReturn(barbeariaMock);
        when(barbeariaRepository.save(any())).thenReturn(barbearia);

        Barbearia resultado = barbeariaService.alterarTelefone(barbearia, telefone);

        assertEquals(telefone, resultado.getTelefoneBarbearia());
        verify(barbeariaRepository).save(barbearia);
    }

    @Test
    @DisplayName("Deve alterar endereço com sucesso")
    void deveAlterarEnderecoComSucesso() {
        Barbearia barbearia = new Barbearia();
        String rua = "Rua A";
        String numero = "10";

        Barbearia enderecoMock = new Barbearia();
        enderecoMock.setRua(rua);
        enderecoMock.setNumeroRua(Integer.valueOf(numero));

        when(barbeariaRepository.findByRuaAndNumeroRua(rua, numero)).thenReturn(enderecoMock);
        when(barbeariaRepository.save(any())).thenReturn(barbearia);

        Barbearia resultado = barbeariaService.alterarEndereco(barbearia, rua, numero);

        assertEquals(rua, resultado.getRua());
        verify(barbeariaRepository).save(barbearia);
    }

    @Test
    @DisplayName("Deve alterar proprietário com sucesso")
    void deveAlterarProprietarioComSucesso() {
        Barbearia barbearia = new Barbearia();
        Proprietario proprietario = new Proprietario();

        Barbearia proprietarioMock = new Barbearia();
        proprietarioMock.setProprietario(proprietario);

        when(barbeariaRepository.findByProprietario(proprietario)).thenReturn(proprietarioMock);
        when(barbeariaRepository.save(any())).thenReturn(barbearia);

        Barbearia resultado = barbeariaService.alterarProprietario(barbearia, proprietario);

        assertEquals(proprietario, resultado.getProprietario());
        verify(barbeariaRepository).save(barbearia);
    }
}