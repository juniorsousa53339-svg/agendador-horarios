package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Servicos;
import com.Luciano.agendador_horarios.infrastructure.repository.ServicosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ServicosService {

    private final ServicosRepository servicosRepository;

    public Servicos salvarServico(Servicos servicos) {

        String nomeServico = servicos.getNomeServico();
        String descricaoServico = servicos.getDescricaoServico();

        Servicos servicoExistente =
                servicosRepository.findByNomeServicoAndDescricaoServico(nomeServico, descricaoServico);

        if (Objects.nonNull(servicoExistente)) {
            throw new RuntimeException("Serviço já está cadastrado.");
        }

        return servicosRepository.save(servicos);
    }

    public void deletarServico(String nomeServico) {
        servicosRepository.deleteByNomeServico(nomeServico);
    }

    public List<Servicos> buscarServico(long idServico,
                                        String nomeServico,
                                        BigDecimal precoServico) {

        return servicosRepository
                .findByIdServicoAndNomeServicoAndPrecoServico(idServico, nomeServico, precoServico);
    }

    public Servicos alterarNomeServico(Servicos servicos, String nomeServico) {

        Servicos servicoExistente = servicosRepository.findByNomeServico(nomeServico);

        if (Objects.isNull(servicoExistente)) {
            throw new RuntimeException("Serviço não encontrado.");
        }

        servicos.setNomeServico(servicoExistente.getNomeServico());
        return servicosRepository.save(servicos);
    }

    public Servicos alterarPrecoServico(Servicos servicos, BigDecimal precoServico) {

        Servicos servicoComPreco = servicosRepository.findByPrecoServico(precoServico);

        if (Objects.isNull(servicoComPreco)) {
            throw new RuntimeException("Preço não encontrado.");
        }

        servicos.setPrecoServico(servicoComPreco.getPrecoServico());
        return servicosRepository.save(servicos);
    }

    public Servicos alterarDescricaoServico(Servicos servicos, String descricaoServico) {

        Servicos servicoExistente =
                servicosRepository.findByNomeServicoAndDescricaoServico(servicos.getNomeServico(),
                        descricaoServico);

        if (Objects.isNull(servicoExistente)) {
            throw new RuntimeException("Descrição não encontrada.");
        }

        servicos.setDescricaoServico(servicoExistente.getDescricaoServico());
        return servicosRepository.save(servicos);
    }
}