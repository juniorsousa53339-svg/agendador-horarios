package com.Luciano.agendador_horarios.service;



import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.infrastructure.repository.FuncionarioRepository;
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
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Test
    @DisplayName("Deve salvar funcionario com sucesso")
    void deveSalvarFuncionariosComSucesso() {

        Funcionario funcionario = new Funcionario();
        funcionario.setNomeFuncionario("Mario");
        funcionario.setTelefoneFuncionario("11777777777");
        funcionario.setEspecialidade("Corte de cabelo Masculino");

        when(funcionarioRepository.findByNomeFuncionario(any())).thenReturn(null);
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        Funcionario resultado = funcionarioService.salvarFuncionario(funcionario);

        assertNotNull(resultado);
        assertEquals("Mario", resultado.getNomeFuncionario());
        verify(funcionarioRepository).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar funcionario já existente")
    void deveLancarExcecaoAoSalvarFuncionarioExistente(){
        Funcionario funcionario = new Funcionario();
        when(funcionarioRepository.findByNomeFuncionario(any())).thenReturn(funcionario);

        assertThrows(RuntimeException.class, () -> funcionarioService.salvarFuncionario(funcionario));
        verify(funcionarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar funcionario com sucesso")
    void deveDeletarFuncionariosComSucesso() {
        String nomeFuncionario = "Mario";

        funcionarioService.deletarFuncionario(nomeFuncionario);

        verify(funcionarioRepository).deleteByNomeFuncionario(nomeFuncionario);
    }

    @Test
    @DisplayName("Deve buscar funcionarios por filtros")
    void deveBuscarFuncionariosComSucesso() {
        when(funcionarioRepository.findByIdFuncionarioAndNomeFuncionario(anyLong(), anyString()))
                .thenReturn(List.of(new Funcionario()));

        List<Funcionario> result = funcionarioService.buscarFuncionario(1L, "Mario");

        assertFalse(result.isEmpty());
        verify(funcionarioRepository).findByIdFuncionarioAndNomeFuncionario(1L, "Mario");
    }

    @Test
    @DisplayName("Deve alterar nome de funcionario")
    void deveAlterarNomeFuncionarioComSucesso() {
        Funcionario funcionario = new Funcionario();
        String nomeFuncionario = "Marc";

        when(funcionarioRepository.findByNomeFuncionario(anyString())).thenReturn(funcionario);
        when(funcionarioRepository.save(any())).thenReturn(funcionario);

        Funcionario resultado = funcionarioService.alterarNomeFuncionario(funcionario,nomeFuncionario);

        assertEquals(nomeFuncionario, resultado.getNomeFuncionario());
        verify(funcionarioRepository).findByNomeFuncionario(nomeFuncionario);
    }

    @Test
    @DisplayName("Deve alterar telefone com sucesso")
    void deveAlterarTelefoneComSucesso() {
        Funcionario funcionario = new Funcionario();
        String telefone = "11999999999";

        Funcionario funcionarioMock = new Funcionario();
        funcionarioMock.setTelefoneFuncionario(telefone);

        when(funcionarioRepository.findByTelefoneFuncionario(telefone)).thenReturn(funcionarioMock);
        when(funcionarioRepository.save(any())).thenReturn(funcionario);

        Funcionario result = funcionarioService.alterarTelefoneFuncionario(funcionario,telefone);

        assertEquals(telefone, result.getTelefoneFuncionario());
        verify(funcionarioRepository).save(funcionario);
    }
}
