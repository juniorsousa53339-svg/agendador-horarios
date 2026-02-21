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

@Service
@RequiredArgsConstructor
public class BarbeariaService {

    private final BarbeariaRepository barbeariaRepository;

    @PreAuthorize("hasRole('Proprietario')")
    public Barbearia salvarBarbearia(Barbearia barbearia) {


        Barbearia barbeariaExistente =
                barbeariaRepository.findByNomeBarbeariaAndProprietario(

                        barbearia.getNomeBarbearia(),
                        barbearia.getProprietario()

                );

        if (Objects.nonNull(barbeariaExistente)) {
            throw new RuntimeException("Barbearia já está cadastrada.");
        }
        return barbeariaRepository.save(barbearia);
    }

    @PreAuthorize("hasRole('Proprietario')")
    public void deletarBarbearia(String nomeBarbearia) {

        Barbearia barbearia =
                barbeariaRepository.findByNomeBarbearia(nomeBarbearia);

        if (Objects.nonNull(barbearia)) {
            throw new RuntimeException("Barbearia não encontrada.");
        }

        barbeariaRepository.deleteByNomeBarbearia(nomeBarbearia);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Barbearia> buscarBarbearia(long idBarbearia, String nomeBarbearia, String rua) {

        List<Barbearia> barbearias =
                barbeariaRepository.findByIdBarbeariaAndNomeBarbeariaAndRua
                        (idBarbearia, nomeBarbearia, rua);

        if (Objects.isNull(barbearias) || barbearias.isEmpty()) {
            throw new RuntimeException("Barbearia não encontrada.");
        }
        return barbearias;
    }

    @PreAuthorize("hasRole('Proprietario')")
    public Barbearia alterarNomeBarbearia(String nomeAtual, String nomeNovo) {

        Barbearia barbeariaExistente =
                barbeariaRepository.findByNomeBarbearia(nomeAtual);

        if (Objects.isNull(barbeariaExistente)) {
            throw new RuntimeException("Barbearia não encontrada.");
        }

        barbeariaExistente.setNomeBarbearia(nomeNovo);
        return barbeariaRepository.save(barbeariaExistente);
    }

    @PreAuthorize("hasRole('Proprietario')")
    public Barbearia alterarHorariosFun(LocalTime horarioAberturaAtu,
                                        LocalTime horarioFechamentoAtu,
                                        LocalTime horarioAberturaNov,
                                        LocalTime horarioFechamentoNov) {

        Barbearia barbeariaComHorario =
                barbeariaRepository.findByHorarioAberturaAndHorarioFechamento(horarioAberturaAtu, horarioFechamentoAtu);

        if (Objects.isNull(barbeariaComHorario)) {
            throw new RuntimeException("Horário de funcionamento não encontrado.");
        }

        barbeariaComHorario.setHorarioAbertura(horarioAberturaNov);
        barbeariaComHorario.setHorarioFechamento(horarioFechamentoNov);
        return barbeariaRepository.save(barbeariaComHorario);
    }

    @PreAuthorize("hasRole('Proprietario')")
    public Barbearia alterarTelefone(String telefoneAtual, String telefoneNovo) {

        Barbearia barbeariaComTelefone =
                barbeariaRepository.findByTelefoneBarbearia(telefoneAtual);

        if (Objects.isNull(barbeariaComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        barbeariaComTelefone.setTelefoneBarbearia(telefoneNovo);
        return barbeariaRepository.save(barbeariaComTelefone);
    }

    @PreAuthorize("hasRole('Proprietario')")
    public Barbearia alterarEndereco

            (
                    String ruaAtual,
                    String numeroRuaAtual,
                    String ruaNova,
                    String numeroRuaNovo
            ) {


        Barbearia barbeariaComEndereco =
                barbeariaRepository.findByRuaAndNumeroRua(ruaAtual, numeroRuaAtual);

        if (Objects.isNull(barbeariaComEndereco)) {
            throw new RuntimeException("Endereço não encontrado para a barbearia informada.");
        }

        barbeariaComEndereco.setRua(ruaNova);
        barbeariaComEndereco.setNumeroRua(Integer.valueOf(numeroRuaNovo));
        return barbeariaRepository.save(barbeariaComEndereco);
    }

    @PreAuthorize("hasRole('Proprietario')")
    public Barbearia alterarProprietario
            (
                    Barbearia barbearia,
                    Proprietario proprietarioAtual,
                    Proprietario proprietarioNovo
            ) {

        Barbearia barbeariaComProprietario =
                barbeariaRepository.findByProprietario(proprietarioAtual);

        if (Objects.isNull(barbeariaComProprietario)) {
            throw new RuntimeException("Proprietário não encontrado.");
        }

        barbeariaComProprietario.setProprietario(proprietarioNovo);
        return barbeariaRepository.save(barbeariaComProprietario);
    }
}