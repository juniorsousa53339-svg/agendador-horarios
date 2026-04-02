package com.Luciano.agendador_horarios.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;


import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servico")
public class Servicos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idServico;


    @NotBlank
    @Size(min=2, max=100) // Determina um min e um max de caracter
    private String nomeServico;

    @NotBlank
    @Size(min=2, max=100)
    private String descricaoServico;

    @NotNull
    @Positive
    private BigDecimal precoServico;

    @NotNull
    @Min(5) // duração mín é de 5 minutos
    private Integer duracaoMinutos;
}
