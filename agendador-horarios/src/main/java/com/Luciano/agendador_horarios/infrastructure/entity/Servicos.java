package com.Luciano.agendador_horarios.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servico")
public class Servicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long idServico;

    @NotNull
    private String nomeServico;

    @NotNull
    private String descricaoServico;

    @NotNull
    private BigDecimal precoServico;
}
