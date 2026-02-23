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

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ServicosRepository servicosRepository;

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    @Transactional
    public AgendamentoResponseDTO criar(AgendamentoRequestDTO dto) {

        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        Funcionario funcionario = funcionarioRepository.findById(dto.idFuncionario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado"));

        Servicos servico = servicosRepository.findById(dto.idServico())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));


        if (agendamentoRepository.existsByFuncionarioAndDataHoraAgendamento(funcionario, dto.dataHoraAgendamento())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Funcionário já possui agendamento nesse horário.");
        }

        Agendamento entity = new Agendamento();
        entity.setCliente(cliente);
        entity.setFuncionario(funcionario);
        entity.setServico(servico);
        entity.setDataHoraAgendamento(dto.dataHoraAgendamento());


        Agendamento salvo = agendamentoRepository.save(entity);

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

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    @Transactional
    public void deletar(LocalDateTime dataHoraAgendamento, Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        Agendamento agendamento = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
        if (agendamento == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado");
        }
        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    @Transactional(readOnly = true)
    public List<Agendamento> buscarDoDia(LocalDate data, Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.atTime(LocalTime.MAX);

        List<Agendamento> lista = agendamentoRepository
                .findByClienteAndDataHoraAgendamentoBetween(cliente, inicio, fim);

        if (lista == null || lista.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum agendamento para o dia selecionado");
        }
        return lista;
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    @Transactional
    public AgendamentoResponseDTO alterar(LocalDateTime dataHoraAtual, Long idCliente,
                                          LocalDateTime dataHoraNova, Long idClienteNovo) {

        Cliente clienteAtual = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente atual não encontrado"));

        Agendamento agenda = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAtual, clienteAtual);
        if (agenda == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Horário não está preenchido");
        }

        Cliente clienteNovo = clienteRepository.findById(idClienteNovo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente novo não encontrado"));


        if (agendamentoRepository.existsByFuncionarioAndDataHoraAgendamento(agenda.getFuncionario(), dataHoraNova)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Funcionário já possui agendamento nesse novo horário.");
        }

        agenda.setCliente(clienteNovo);
        agenda.setDataHoraAgendamento(dataHoraNova);

        Agendamento salvo = agendamentoRepository.save(agenda);

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