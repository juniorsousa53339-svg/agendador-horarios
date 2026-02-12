package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.repository.BarbeariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BarbeariaService {

    private final BarbeariaRepository barbeariaRepository;

    public Barbearia salvarBarbearia(Barbearia barbearia) {

        String nomeBarbearia = barbearia.getNomeBarbearia();
        Proprietario proprietario = barbearia.getProprietario();

        Barbearia barbeariaExistente = barbeariaRepository.findByNomeBarbeariaAndProprietario(nomeBarbearia, proprietario);

        if (Objects.nonNull(barbeariaExistente)) {
            throw new RuntimeException("Barbearia já está cadastrada.");
        }
        return barbeariaRepository.save(barbearia);
    }

    public void deletarBarbearia(String nomeBarbearia) {
        barbeariaRepository.deleteByNomeBarbearia(nomeBarbearia);
    }

    public List<Barbearia> buscarBarbearia(long idBarbearia, String nomeBarbearia, String rua) {
        return barbeariaRepository.findByIdBarbeariaAndNomeBarbeariaAndRua(idBarbearia, nomeBarbearia, rua);
    }

    public Barbearia alterarNomeBarbearia(Barbearia barbearia, String nomeBarbearia) {
        Barbearia barbeariaExistente = barbeariaRepository.findByNomeBarbearia(nomeBarbearia);

        if (Objects.isNull(barbeariaExistente)) {
            throw new RuntimeException("Barbearia não encontrada.");
        }

        barbearia.setNomeBarbearia(nomeBarbearia);
        return barbeariaRepository.save(barbearia);
    }

    public Barbearia alterarHorariosFun(Barbearia barbearia, LocalTime horarioAbertura, LocalTime horarioFechamento) {
        Barbearia barbeariaComHorario = barbeariaRepository.findByHorarioAberturaAndHorarioFechamento(horarioAbertura, horarioFechamento);

        if (Objects.isNull(barbeariaComHorario)) {
            throw new RuntimeException("Horário de funcionamento não encontrado.");
        }

        barbearia.setHorarioAbertura(barbeariaComHorario.getHorarioAbertura());
        barbearia.setHorarioFechamento(barbeariaComHorario.getHorarioFechamento());
        return barbeariaRepository.save(barbearia);
    }

    public Barbearia alterarTelefone(Barbearia barbearia, String telefoneBarbearia) {
        Barbearia barbeariaComTelefone = barbeariaRepository.findByTelefoneBarbearia(telefoneBarbearia);

        if (Objects.isNull(barbeariaComTelefone)) {
            throw new RuntimeException("Telefone não encontrado.");
        }

        barbearia.setTelefoneBarbearia(barbeariaComTelefone.getTelefoneBarbearia());
        return barbeariaRepository.save(barbearia);
    }

    public Barbearia alterarEndereco(Barbearia barbearia, String rua, String numeroRua) {
        Barbearia barbeariaComEndereco = barbeariaRepository.findByRuaAndNumeroRua(rua, numeroRua);

        if (Objects.isNull(barbeariaComEndereco)) {
            throw new RuntimeException("Endereço não encontrado para a barbearia informada.");
        }

        barbearia.setRua(barbeariaComEndereco.getRua());
        barbearia.setNumeroRua(barbeariaComEndereco.getNumeroRua());
        return barbeariaRepository.save(barbearia);
    }

    public Barbearia alterarProprietario(Barbearia barbearia, Proprietario proprietario) {
        Barbearia barbeariaComProprietario = barbeariaRepository.findByProprietario(proprietario);

        if (Objects.isNull(barbeariaComProprietario)) {
            throw new RuntimeException("Proprietário não encontrado.");
        }

        barbearia.setProprietario(barbeariaComProprietario.getProprietario());
        return barbeariaRepository.save(barbearia);
    }
}