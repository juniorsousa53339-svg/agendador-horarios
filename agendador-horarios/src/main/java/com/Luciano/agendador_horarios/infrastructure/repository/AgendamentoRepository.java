package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Agendamento;
import com.Luciano.agendador_horarios.infrastructure.entity.Cliente;
import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repositório responsável pela persistência e consulta de dados de agendamentos.
 * Utiliza Spring Data JPA para abstração da camada de dados.
 */
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {


    boolean existsByFuncionarioAndDataHoraAgendamento(Funcionario funcionario, LocalDateTime dataHora);

    /**
     * Verifica se já existe um agendamento para um funcionário específico em um determinado horário.
     * Útil para validações de conflito de agenda antes de salvar um novo registro.
     * * @param funcionario O profissional a ser verificado.
     *
     * @param "dataHora" O horário solicitado.
     * @return true se o funcionário já estiver ocupado, false caso contrário.
     */
    List<Agendamento> findByClienteAndDataHoraAgendamentoBetween(
            Cliente cliente,
            LocalDateTime inicio,
            LocalDateTime fim
    );


    /**
     * Busca um agendamento específico baseado no horário e no cliente.
     * * @param dataHora Horário exato do agendamento.
     * @param cliente Cliente que realizou a reserva.
     * @return O objeto Agendamento ou null caso não exista.
     */
    Agendamento findByDataHoraAgendamentoAndCliente(LocalDateTime dataHora, Cliente cliente);


    /**
     * Remove um agendamento do banco de dados para um cliente em um horário específico.
     * Implementa a regra de cancelamento de horários.
     * * @param dataHora Horário do agendamento a ser removido.
     * @param cliente Cliente solicitante da remoção.
     */
    void deleteByDataHoraAgendamentoAndCliente(LocalDateTime dataHora, Cliente cliente);
}
