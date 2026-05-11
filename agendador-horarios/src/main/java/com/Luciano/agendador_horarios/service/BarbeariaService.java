package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.repository.BarbeariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Service responsável pela gestão administrativa da barbearia.
 * Controla dados do estabelecimento como endereço, horários e vínculo com proprietário.
 */
@Service
@RequiredArgsConstructor
public class BarbeariaService {

    private final BarbeariaRepository barbeariaRepository;

    /**
     * Registra uma nova unidade de barbearia após verificar duplicidade por nome e proprietário.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Barbearia salvarBarbearia(Barbearia barbearia, Proprietario proprietario) {
        Barbearia barbeariaExistente = barbeariaRepository.findByNomeBarbeariaAndProprietario(
                barbearia.getNomeBarbearia(),
                proprietario
        );

        if (Objects.nonNull(barbeariaExistente)) {
            throw new RuntimeException("Barbearia já está cadastrada.");
        }
        return barbeariaRepository.save(barbearia);
    }

    /**
     * Remove o registro de uma barbearia pelo nome.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public void deletarBarbearia(String nomeBarbearia) {
        Barbearia barbearia = barbeariaRepository.findByNomeBarbearia(nomeBarbearia);

        if (Objects.isNull(barbearia)) { // Ajuste lógico: se for nulo, não encontra
            throw new RuntimeException("Barbearia não encontrada.");
        }

        barbeariaRepository.delete(barbearia);
    }

    /**
     * Busca barbearias utilizando filtros combinados de ID, Nome e Rua.
     */
    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Barbearia> buscarBarbearia(UUID idBarbearia, String nomeBarbearia, String rua) {
        List<Barbearia> barbearias = barbeariaRepository.findByIdBarbeariaAndNomeBarbeariaAndRua(
                idBarbearia, nomeBarbearia, rua);

        if (Objects.isNull(barbearias) || barbearias.isEmpty()) {
            throw new RuntimeException("Barbearia não encontrada.");
        }

        return barbearias;
    }

    /**
     * Atualiza o nome comercial da barbearia cadastrada.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Barbearia alterarNomeBarbearia(String nomeBarbeariaAtual, String nomeBarbeariaNovo) {
        Barbearia barbeariaExistente = barbeariaRepository.findByNomeBarbearia(nomeBarbeariaAtual);

        if (Objects.isNull(barbeariaExistente)) {
            throw new RuntimeException("Barbearia não encontrada.");
        }

        barbeariaExistente.setNomeBarbearia(nomeBarbeariaNovo);
        return barbeariaRepository.save(barbeariaExistente);
    }

    /**
     * Altera o intervalo de funcionamento (abertura e fechamento) do estabelecimento.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Barbearia alterarHorariosFun(LocalTime horarioAberturaAtual, LocalTime horarioAberturaNovo,
                                        LocalTime horarioFechamentoAtual, LocalTime horarioFechamentoNovo) {
        Barbearia barbeariaComHorario = barbeariaRepository.findByHorarioAberturaAndHorarioFechamento(
                horarioAberturaAtual,
                horarioFechamentoAtual
        );

        if (Objects.isNull(barbeariaComHorario)) {
            throw new RuntimeException("Horário de funcionamento não encontrado.");
        }

        barbeariaComHorario.setHorarioAbertura(horarioAberturaNovo);
        barbeariaComHorario.setHorarioFechamento(horarioFechamentoNovo);
        return barbeariaRepository.save(barbeariaComHorario);
    }

    /**
     * Atualiza o telefone de contato oficial da barbearia.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Barbearia alterarTelefone(String telefoneAtual, String telefoneNovo) {
        Barbearia barbeariaComTelefone = barbeariaRepository.findByTelefoneBarbearia(telefoneAtual);

        if (Objects.isNull(barbeariaComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        barbeariaComTelefone.setTelefoneBarbearia(telefoneNovo);
        return barbeariaRepository.save(barbeariaComTelefone);
    }

    /**
     * Atualiza o endereço completo (rua e número) da unidade.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Barbearia alterarEndereco(String ruaAtual, String ruaNova, int numeroRuaAtual, String numeroRuaNova) {
        Barbearia barbeariaComEndereco = barbeariaRepository.findByRuaAndNumeroRua(ruaAtual, numeroRuaAtual);

        if (Objects.isNull(barbeariaComEndereco)) {
            throw new RuntimeException("Endereço não encontrado para a barbearia informada.");
        }

        barbeariaComEndereco.setRua(ruaNova);
        barbeariaComEndereco.setNumeroRua(Integer.valueOf(numeroRuaNova));
        return barbeariaRepository.save(barbeariaComEndereco);
    }

    /**
     * Transfere ou atualiza o proprietário vinculado à barbearia.
     */
    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Barbearia alterarProprietario(Proprietario proprietarioAtual, Proprietario proprietarioNovo) {
        Barbearia barbeariaComProprietario = barbeariaRepository.findByProprietario(proprietarioAtual);

        if (Objects.isNull(barbeariaComProprietario)) {
            throw new RuntimeException("Proprietário não encontrado.");
        }

        barbeariaComProprietario.setProprietario(proprietarioNovo);
        return barbeariaRepository.save(barbeariaComProprietario);
    }
}