package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.DTO.AgendamentoRequestDTO;
import com.Luciano.agendador_horarios.DTO.AgendamentoResponseDTO;
import com.Luciano.agendador_horarios.infrastructure.entity.Agendamento;
import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import com.Luciano.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import com.Luciano.agendador_horarios.infrastructure.repository.ClienteRepository;
import com.Luciano.agendador_horarios.infrastructure.repository.FuncionarioRepository;
import com.Luciano.agendador_horarios.infrastructure.repository.ServicosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * Service responsável pela lógica de negócio dos agendamentos.
 * Gerencia validações de disponibilidade e permissões de acesso.
 */
@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ServicosRepository servicosRepository;

    /**
     * Registra um novo agendamento após validar a existência das entidades e conflitos de horário.
     */
    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    @Transactional
    public AgendamentoResponseDTO criar(AgendamentoRequestDTO dto) {
        // Validação de existência das entidades relacionadas
        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        Funcionario funcionario = funcionarioRepository.findById(dto.idFuncionario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado"));

        Servicos servico = servicosRepository.findById(dto.idServico())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));

        // Regra de negócio: impede duplicidade de horário para o mesmo funcionário
        if (agendamentoRepository.existsByFuncionarioAndDataHoraAgendamento(funcionario, dto.dataHoraAgendamento())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Funcionário já possui agendamento nesse horário.");
        }

        Agendamento entity = new Agendamento();
        entity.setCliente(cliente);
        entity.setFuncionario(funcionario);
        entity.setServico(servico);
        entity.setDataHoraAgendamento(dto.dataHoraAgendamento());

        Agendamento salvo = agendamentoRepository.save(entity);

        return mapperParaResponse(salvo);
    }

    /**
     * Remove um agendamento específico baseado no horário e identificador do cliente.
     */
    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    @Transactional
    public void deletar(LocalDateTime dataHoraAgendamento, UUID idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        Agendamento agendamento = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
        if (agendamento == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado");
        }
        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }

    /**
     * Consulta agendamentos de um cliente em uma data específica.
     */
    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    @Transactional(readOnly = true)
    public List<Agendamento> buscarDoDia(LocalDate data, UUID idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        // Define o range do dia (00:00:00 até 23:59:59)
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.atTime(LocalTime.MAX);

        List<Agendamento> lista = agendamentoRepository
                .findByClienteAndDataHoraAgendamentoBetween(cliente, inicio, fim);

        if (lista == null || lista.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum agendamento para o dia selecionado");
        }
        return lista;
    }

    /**
     * Altera o horário ou o cliente de um agendamento existente, validando a nova disponibilidade.
     */
    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    @Transactional
    public AgendamentoResponseDTO alterar(LocalDateTime dataHoraAtual, UUID idCliente,
                                          LocalDateTime dataHoraNova, UUID idClienteNovo) {

        Cliente clienteAtual = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente atual não encontrado"));

        Agendamento agenda = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAtual, clienteAtual);
        if (agenda == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Horário não está preenchido");
        }

        Cliente clienteNovo = clienteRepository.findById(idClienteNovo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente novo não encontrado"));

        // Valida se o novo horário está livre para o funcionário vinculado
        if (agendamentoRepository.existsByFuncionarioAndDataHoraAgendamento(agenda.getFuncionario(), dataHoraNova)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Funcionário já possui agendamento nesse novo horário.");
        }

        agenda.setCliente(clienteNovo);
        agenda.setDataHoraAgendamento(dataHoraNova);

        Agendamento salvo = agendamentoRepository.save(agenda);

        return mapperParaResponse(salvo);
    }

    /**
     * Método auxiliar para converter a entidade em DTO de resposta.
     */
    private AgendamentoResponseDTO mapperParaResponse(Agendamento salvo) {
        return new AgendamentoResponseDTO(
                salvo.getIdAgendamento(),
                salvo.getCliente().getIdCliente(),
                salvo.getCliente().getNomeCliente(),
                salvo.getFuncionario().getIdFuncionario(),
                salvo.getFuncionario().getNomeFuncionario(),
                salvo.getServico().getIdServico(),
                salvo.getServico().getNomeServico(),
                salvo.getDataHoraAgendamento(),
                "MARCADO"
        );
    }
}