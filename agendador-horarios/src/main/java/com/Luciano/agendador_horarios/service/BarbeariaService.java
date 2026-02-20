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

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Barbearia salvarBarbearia(Barbearia barbearia) {

        String nomeBarbearia = barbearia.getNomeBarbearia();
        Proprietario proprietario = barbearia.getProprietario();

        Barbearia barbeariaExistente = null;
        barbeariaExistente = barbeariaRepository.findByNomeBarbeariaAndProprietario(nomeBarbearia, proprietario);

        if (Objects.nonNull(barbeariaExistente)) {
            throw new RuntimeException("Barbearia já está cadastrada.");
        }
        return barbeariaRepository.save(barbearia);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public void deletarBarbearia(String nomeBarbearia) {

        Barbearia barbearia = null;
        barbearia = barbeariaRepository.findByNomeBarbearia(nomeBarbearia);

        if (Objects.isNull(barbearia)) {
            throw new RuntimeException("Barbearia não encontrada.");
        }

        barbeariaRepository.deleteByNomeBarbearia(nomeBarbearia);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO','FUNCIONARIO')")
    public List<Barbearia> buscarBarbearia(long idBarbearia, String nomeBarbearia, String rua) {

        List<Barbearia> barbearias = null;
        barbearias = barbeariaRepository.findByIdBarbeariaAndNomeBarbeariaAndRua(idBarbearia, nomeBarbearia, rua);

        if (Objects.isNull(barbearias) || barbearias.isEmpty()) {
            throw new RuntimeException("Barbearia não encontrada.");
        }

        return barbearias;
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Barbearia alterarNomeBarbearia(Barbearia barbearia, String nomeBarbearia) {

        Barbearia barbeariaExistente = null;
        barbeariaExistente = barbeariaRepository.findByNomeBarbearia(nomeBarbearia);

        if (Objects.isNull(barbeariaExistente)) {
            throw new RuntimeException("Barbearia não encontrada.");
        }

        barbearia.setNomeBarbearia(nomeBarbearia);
        return barbeariaRepository.save(barbearia);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Barbearia alterarHorariosFun(Barbearia barbearia, LocalTime horarioAbertura, LocalTime horarioFechamento) {

        Barbearia barbeariaComHorario = null;
        barbeariaComHorario = barbeariaRepository.findByHorarioAberturaAndHorarioFechamento(horarioAbertura, horarioFechamento);

        if (Objects.isNull(barbeariaComHorario)) {
            throw new RuntimeException("Horário de funcionamento não encontrado.");
        }

        barbearia.setHorarioAbertura(barbeariaComHorario.getHorarioAbertura());
        barbearia.setHorarioFechamento(barbeariaComHorario.getHorarioFechamento());
        return barbeariaRepository.save(barbearia);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Barbearia alterarTelefone(Barbearia barbearia, String telefoneBarbearia) {

        Barbearia barbeariaComTelefone = null;
        barbeariaComTelefone = barbeariaRepository.findByTelefoneBarbearia(telefoneBarbearia);

        if (Objects.isNull(barbeariaComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        barbearia.setTelefoneBarbearia(barbeariaComTelefone.getTelefoneBarbearia());
        return barbeariaRepository.save(barbearia);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Barbearia alterarEndereco(Barbearia barbearia, String rua, String numeroRua) {

        Barbearia barbeariaComEndereco = null;
        barbeariaComEndereco = barbeariaRepository.findByRuaAndNumeroRua(rua, numeroRua);

        if (Objects.isNull(barbeariaComEndereco)) {
            throw new RuntimeException("Endereço não encontrado para a barbearia informada.");
        }

        barbearia.setRua(barbeariaComEndereco.getRua());
        barbearia.setNumeroRua(barbeariaComEndereco.getNumeroRua());
        return barbeariaRepository.save(barbearia);
    }

    @PreAuthorize("hasRole('PROPRIETARIO')")
    public Barbearia alterarProprietario(Barbearia barbearia, Proprietario proprietario) {

        Barbearia barbeariaComProprietario = null;
        barbeariaComProprietario = barbeariaRepository.findByProprietario(proprietario);

        if (Objects.isNull(barbeariaComProprietario)) {
            throw new RuntimeException("Proprietário não encontrado.");
        }

        barbearia.setProprietario(barbeariaComProprietario.getProprietario());
        return barbeariaRepository.save(barbearia);
    }
}
