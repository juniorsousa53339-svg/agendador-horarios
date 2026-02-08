package com.Luciano.agendador_horarios.infrastructure.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

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
    private long id_barbearia;

    @ManyToOne
    @JoinColumn(name = "proprietario")
    private Proprietario proprietario;

    @NotNull
    private String nomeBarbearia;

    @NotNull
    private String endereco;

    @NotNull
    private int numeroRua;

    @NotNull
    private String telefoneBarbearia;

    @NotNull
    private LocalTime horarioAbertura;

    @NotNull
    private LocalTime horarioFechamento;
}
