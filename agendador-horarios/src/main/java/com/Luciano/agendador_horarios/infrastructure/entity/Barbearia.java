package com.Luciano.agendador_horarios.infrastructure.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "barbearia")
public class Barbearia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBarbearia;

    @ManyToOne
    @JoinColumn(name = "proprietario")
    private Proprietario proprietario;

    @NotBlank
    private String nomeBarbearia;

    @NotBlank
    private String rua;


    private int numeroRua;

    @NotBlank
    private String telefoneBarbearia;

    @NotNull
    private LocalTime horarioAbertura;

    @NotNull
    private LocalTime horarioFechamento;
}
