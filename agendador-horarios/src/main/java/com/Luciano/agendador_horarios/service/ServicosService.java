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

/**
 * Service responsável pela gestão do catálogo de serviços.
 * Controla a criação, precificação e tempo de duração das atividades da barbearia.
 */
@Service
@RequiredArgsConstructor
public class ServicosService {

    private final ServicosRepository servicosRepository;

    /**
     * Cadastra um novo serviço validando se todos os atributos principais já existem.
     * Evita duplicidade de itens idênticos no catálogo.
     */
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

        return servicosRepository.save(servicos);
    }

    /**
     * Remove um serviço do catálogo pelo nome comercial.
     */
    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public void deletarServico(String nomeServico) {
        Servicos servico = servicosRepository.findByNomeServico(nomeServico);

        if (Objects.isNull(servico)) {
            throw new RuntimeException("Serviço não encontrado.");
        }

        servicosRepository.deleteByNomeServico(nomeServico);
    }

    /**
     * Consulta serviços utilizando filtros de ID, Nome e Preço.
     */
    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Servicos> buscarServico(UUID idServico, String nomeServico, BigDecimal precoServico) {
        List<Servicos> servicos = servicosRepository
                .findByIdServicoAndNomeServicoAndPrecoServico(idServico, nomeServico, precoServico);

        if (Objects.isNull(servicos) || servicos.isEmpty()) {
            throw new RuntimeException("Serviço não encontrado.");
        }

        return servicos;
    }

    /**
     * Atualiza o nome de um serviço existente.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Servicos alterarNomeServico(String nomeServicoAtual, String nomeServicoNovo) {
        Servicos servicoExistente = servicosRepository.findByNomeServico(nomeServicoAtual);

        if (Objects.isNull(servicoExistente)) {
            throw new RuntimeException("Serviço não encontrado.");
        }

        servicoExistente.setNomeServico(nomeServicoNovo);
        return servicosRepository.save(servicoExistente);
    }

    /**
     * Atualiza o valor cobrado por um serviço específico.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Servicos alterarPrecoServico(BigDecimal precoServicoAtual, BigDecimal precoServicoNovo) {
        Servicos servicoComPreco = servicosRepository.findByPrecoServico(precoServicoAtual);

        if (Objects.isNull(servicoComPreco)) {
            throw new RuntimeException("Preço não encontrado.");
        }

        servicoComPreco.setPrecoServico(precoServicoNovo);
        return servicosRepository.save(servicoComPreco);
    }

    /**
     * Altera a descrição detalhada do serviço.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Servicos alterarDescricaoServico(String descricaoAtual, String descricaoNova) {
        Servicos servicoExistente = servicosRepository.findByDescricaoServico(descricaoAtual);

        if (Objects.isNull(servicoExistente)) {
            throw new RuntimeException("Descrição não encontrada.");
        }

        servicoExistente.setDescricaoServico(descricaoNova);
        return servicosRepository.save(servicoExistente);
    }

    /**
     * Ajusta o tempo médio de duração do serviço para fins de organização da agenda.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Servicos alterarDuracaoServico(Integer duracaoAtual, Integer duracaoNova) {
        Servicos servicoComDuracao = servicosRepository.findByDuracaoMinutos(duracaoAtual);

        if (Objects.isNull(servicoComDuracao)) {
            throw new RuntimeException("Duração não encontrada.");
        }

        servicoComDuracao.setDuracaoMinutos(duracaoNova);
        return servicosRepository.save(servicoComDuracao);
    }
}