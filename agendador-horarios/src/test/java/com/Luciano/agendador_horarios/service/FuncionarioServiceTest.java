package com.Luciano.agendador_horarios.service;


import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.infrastructure.repository.FuncionarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
