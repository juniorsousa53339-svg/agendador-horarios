package com.Luciano.agendador_horarios.infrastructure.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idFuncionario;

    @NotNull
    private String nomeFuncionario;

    @NotNull
    private String telefoneFuncionario;

    @NotNull
    private String especialidade;
}
