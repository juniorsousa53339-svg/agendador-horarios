package com.Luciano.agendador_horarios.DTO;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class AlterarServicosResponse {

    @NotBlank
    private String nomeServico;

    @NotBlank
    private String descricaoServico;

    @NotNull
    private BigDecimal precoServico;

    @NotNull
    @Min(5)
    private Integer duracaoMinutos;
}
