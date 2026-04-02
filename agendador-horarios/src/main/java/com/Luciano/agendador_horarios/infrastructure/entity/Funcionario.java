package com.Luciano.agendador_horarios.infrastructure.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idFuncionario;

    @NotBlank
    @Size(min=2, max=100) // Determina um min e um max de caracter
    private String nomeFuncionario;

    @Pattern(regexp = "\\\\(\\\\d{2}\\\\) \\\\d{5}-\\\\d{4}") // Valida o formato do telefone no padrão (XX) XXXXX-XXXX
    @NotBlank
    private String telefoneFuncionario;

    @NotBlank
    @Size(min=2, max=100) // Determina um min e um max de caracter
    private String especialidade;
}
