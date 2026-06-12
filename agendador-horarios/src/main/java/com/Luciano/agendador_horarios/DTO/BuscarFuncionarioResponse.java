package com.Luciano.agendador_horarios.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BuscarFuncionarioResponse {

    @NotBlank
    String nomeFuncionario;

    @NotNull
    String telefoneFuncionario;

    @NotBlank
    String especialidade;

    @NotNull
    String email;

    @NotNull
    String senha;


    public void reset() {
        nomeFuncionario = "";
        telefoneFuncionario = "";
        especialidade = "";
        email = "";
        senha = "";
    }

}
