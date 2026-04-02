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
@Table(name = "proprietario")
public class Proprietario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProprietario;

    @NotBlank
    @Size(min=2, max=100) // Determina um min e um max de caracter
    private String nome;

    @Pattern(regexp = "\\\\(\\\\d{2}\\\\) \\\\d{5}-\\\\d{4}") // Valida o formato do telefone no padrão (XX) XXXXX-XXXX
    @NotBlank
    private String telefone;

    @Email
    private String email;
}
