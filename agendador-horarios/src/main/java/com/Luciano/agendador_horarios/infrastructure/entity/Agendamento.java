package com.Luciano.agendador_horarios.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long idAgendamento;

    @NotNull
    private LocalDateTime dataHoraAgendamento;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servicos servico;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente idCliente;

    @NotNull
    private LocalDateTime dataInsercao = LocalDateTime.now();

}
