package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import com.Luciano.agendador_horarios.infrastructure.repository.ServicosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ServicosService {

    private final ServicosRepository servicosRepository;

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Servicos salvarServico(Servicos servicos) {

        Servicos servicoExistente =
                servicosRepository.findByNomeServicoAndDescricaoServicoAndPrecoServico(

                        servicos.getNomeServico(),
                        servicos.getDescricaoServico(),
                        servicos.getPrecoServico()

                );

        if (servicoExistente == null) {
            throw new RuntimeException("Serviço já está cadastrado.");
        }

        return servicosRepository.save(servicos);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public void deletarServico(String nomeServico) {

        Servicos servico =
                servicosRepository.findByNomeServico(nomeServico);

        if (Objects.isNull(servico)) {
            throw new RuntimeException("Serviço não encontrado.");
        }

        servicosRepository.deleteByNomeServico(nomeServico);
    }


    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Servicos> buscarServico(long idServico,
                                        String nomeServico,
                                        BigDecimal precoServico) {

        List<Servicos> servicos =
                servicosRepository
                .findByIdServicoAndNomeServicoAndPrecoServico
                        (idServico, nomeServico, precoServico);

        if (Objects.isNull(servicos) || servicos.isEmpty()) {
            throw new RuntimeException("Serviço não encontrado.");
        }

        return servicos;
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Servicos alterarNomeServico(String nomeServicoAtual, String nomeServicoNovo) {

        Servicos servicoExistente =
                servicosRepository.findByNomeServico(nomeServicoAtual);

        if (Objects.isNull(servicoExistente)) {
            throw new RuntimeException("Serviço não encontrado.");
        }

        servicoExistente.setNomeServico(nomeServicoNovo);
        return servicosRepository.save(servicoExistente);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Servicos alterarPrecoServico(BigDecimal precoServicoAtual, BigDecimal precoServicoNovo) {

        Servicos servicoComPreco =
                servicosRepository.findByPrecoServico(precoServicoAtual);

        if (Objects.isNull(servicoComPreco)) {
            throw new RuntimeException("Preço não encontrado.");
        }

        servicoComPreco.setPrecoServico(precoServicoNovo);
        return servicosRepository.save(servicoComPreco);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Servicos alterarDescricaoServico( String descricaoAtual,String descricaoNova) {

        Servicos servicoExistente =
                servicosRepository.findByDescricaoServico(
                        descricaoAtual);

        if (Objects.isNull(servicoExistente)) {
            throw new RuntimeException("Descrição não encontrada.");
        }

        servicoExistente.setDescricaoServico(descricaoNova);
        return servicosRepository.save(servicoExistente);
    }
}
