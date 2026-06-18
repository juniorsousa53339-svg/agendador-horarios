package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import com.Luciano.agendador_horarios.infrastructure.repository.ServicosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicosService {

    private final ServicosRepository servicosRepository;

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Servicos salvarServico(Servicos servicos) {

        Servicos servicoExistente = servicosRepository
                .findByNomeServicoAndDescricaoServicoAndPrecoServicoAndDuracaoMinutos(
                        servicos.getNomeServico(),
                        servicos.getDescricaoServico(),
                        servicos.getPrecoServico(),
                        servicos.getDuracaoMinutos()
                );

        if (servicoExistente != null) {
            throw new RuntimeException("Serviço já está cadastrado.");
        }

        return servicosRepository.
                save(servicos);
    }

     @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public void deletarServico(String nomeServico) {
        Servicos servico =
                servicosRepository.
                        findByNomeServico
                                (nomeServico);

        if (Objects.isNull(servico)) {
            throw new RuntimeException("Serviço não encontrado.");
        }

        servicosRepository.
                deleteByNomeServico(nomeServico);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public Servicos buscarServico(UUID idServico) {

        Servicos servicosBuscados =
                servicosRepository
                        .findById(idServico)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Servico não encontrado."
                                ));

        return servicosBuscados;
    }

    public Servicos alterarDados(

            UUID idServico,
            String nomeServico,
            String descricaoServico,
            BigDecimal precoServico,
            Integer duracaoMinutos
    ){

        Servicos serviceComDados =
                servicosRepository.findById(idServico)
                        .orElseThrow(()
                                -> new RuntimeException("Serviço não encontrado.")
                        );

        serviceComDados.alterarDados(

                nomeServico,
                descricaoServico,
                precoServico,
                duracaoMinutos
        );

        return servicosRepository.
                save(serviceComDados);
    }

    public List<Servicos> listarTodos() {
        return servicosRepository.findAll();
    }

}