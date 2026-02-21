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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Test
    @DisplayName("Deve salvar funcionário com sucesso")
    void deveSalvarFuncionarioComSucesso() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNomeFuncionario("Mario");

        when(funcionarioRepository.findByNomeFuncionario("Mario")).thenReturn(null);
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        Funcionario resultado = funcionarioService.salvarFuncionario(funcionario);

        assertNotNull(resultado);
        assertEquals("Mario", resultado.getNomeFuncionario());
    }

    @Test
    @DisplayName("Deve deletar funcionário existente")
    void deveDeletarFuncionarioComSucesso() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNomeFuncionario("Mario");

        when(funcionarioRepository.findByNomeFuncionario("Mario")).thenReturn(funcionario);

        funcionarioService.deletarFuncionario("Mario");

        verify(funcionarioRepository, times(1)).deleteByNomeFuncionario("Mario");
    }

    @Test
    @DisplayName("Deve buscar funcionário por id e nome")
    void deveBuscarFuncionarioComSucesso() {
        when(funcionarioRepository.findByIdFuncionarioAndNomeFuncionario(1L, "Mario"))
                .thenReturn(List.of(new Funcionario()));

        var resultado = funcionarioService.buscarFuncionario(1L, "Mario");

        assertEquals(1, resultado.size());
    }
}
