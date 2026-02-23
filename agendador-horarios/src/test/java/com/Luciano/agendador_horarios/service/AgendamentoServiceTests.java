package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.DTO.AgendamentoRequestDTO;
import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import com.Luciano.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import com.Luciano.agendador_horarios.infrastructure.repository.ClienteRepository;
import com.Luciano.agendador_horarios.infrastructure.repository.FuncionarioRepository;
import com.Luciano.agendador_horarios.infrastructure.repository.ServicosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @InjectMocks
    AgendamentoService service;
    @Mock
    AgendamentoRepository repo;
    @Mock
    ClienteRepository clienteRepo;
    @Mock
    FuncionarioRepository funcRepo;
    @Mock
    ServicosRepository servRepo;

    @Test
    void naoDevePermitirConflito() {
        var dto = new AgendamentoRequestDTO(1L,10L,3L, LocalDateTime.parse("2026-02-23T18:30:00"));
        when(clienteRepo.findById(1L)).thenReturn(Optional.of(new Cliente()));
        when(funcRepo.findById(10L)).thenReturn(Optional.of(new Funcionario()));
        when(servRepo.findById(3L)).thenReturn(Optional.of(new Servicos()));
        when(repo.existsByFuncionarioAndDataHoraAgendamento(any(), any())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> service.criar(dto));
    }
}

