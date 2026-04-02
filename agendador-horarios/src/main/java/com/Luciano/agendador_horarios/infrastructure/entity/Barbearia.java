package com.Luciano.agendador_horarios.infrastructure.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.time.LocalTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "barbearia")
public class Barbearia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idBarbearia;

    @ManyToOne
    @JoinColumn(name = "proprietario")
    private Proprietario proprietario;


    @NotBlank
    @Size(min=2, max=100) // Determina um min e um max de caracter
    private String nomeBarbearia;


    @NotBlank
    @Size(min=2, max=100)
    private String rua;

    @Positive
    private int numeroRua;

    @NotBlank
   @Pattern(regexp = "\\\\(\\\\d{2}\\\\) \\\\d{5}-\\\\d{4}")
    // Valida o formato do telefone no padrão (XX) XXXXX-XXXX
    private String telefoneBarbearia;

    @NotNull
    private LocalTime horarioAbertura;

    @NotNull
    private LocalTime horarioFechamento;
}
