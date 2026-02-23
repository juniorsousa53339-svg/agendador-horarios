package com.Luciano.agendador_horarios.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;


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

    @NotBlank
    private String nomeServico;

    @NotBlank
    private String descricaoServico;

    @NotNull
    private BigDecimal precoServico;
}
