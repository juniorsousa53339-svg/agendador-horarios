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
        String rua = barbearia.getRua();
        Proprietario proprietario = barbearia.getProprietario();
        int numeroRua = barbearia.getNumeroRua();

        Barbearia barbearias = barbeariaRepository.findByNomeBarbeariaAndProprietario
                (nomeBarbearia, proprietario);

        if (Objects.nonNull(barbearias)) {
            throw new RuntimeException("Barbearia já está preenchido");
        }
        return barbeariaRepository.save(barbearia);
    }

    public void deletarBarbearia(String nomeBarbearia) {
        barbeariaRepository.deleteByNomeBarbearia(nomeBarbearia);
    }

    public List<Barbearia> buscarBarbearia(String nomeBarbearia, long idBarbearia, String rua) {
        String nomebarbearia = nomeBarbearia;

        return barbeariaRepository.findByIdBarbeariaAndNomebarbeariaAndRuaAndnumeroRua
                (idBarbearia, nomeBarbearia, rua);
    }

    public Barbearia alterarNomeBarbearia(Barbearia barbearia, String nomeBarbearia) {

        Barbearia nomeBarbeariaAlt = barbeariaRepository.findByNomeBarbearia(nomeBarbearia);

        if (Objects.isNull(nomeBarbeariaAlt)) {
            throw new RuntimeException("Barbearia não está preenchido");
        }

        barbearia.setIdBarbearia(nomeBarbeariaAlt.getIdBarbearia());
        return barbeariaRepository.save(nomeBarbeariaAlt);

    }

    public Barbearia alterarHorariosFun(Barbearia barbearia, LocalTime horarioAbertura, LocalTime horarioFechamento) {
        Barbearia HorarioFun = barbeariaRepository.findybyBarbeariaAndHorarioAberturaAndHorarioFechamento
                (barbearia, horarioAbertura, horarioFechamento);

        if (Objects.isNull(HorarioFun)) {
            throw new RuntimeException("Horário de funcionamento não encontrado.");
        }
        barbearia.setHorarioAbertura(HorarioFun.getHorarioAbertura());
        barbearia.setHorarioFechamento(HorarioFun.getHorarioFechamento());
        return barbeariaRepository.save(barbearia);
    }

    public Barbearia alterarTelefone(Barbearia barbearia, String telefoneBarbearia) {
        Barbearia altTelefone = barbeariaRepository.findybyBarbeariaAndTelefoneBarbearia(barbearia, telefoneBarbearia);

        if (Objects.isNull(altTelefone)) {
            throw new RuntimeException("Horário de funcionamento não encontrado.");
        }
        barbearia.setTelefoneBarbearia(altTelefone.getTelefoneBarbearia());
        return barbeariaRepository.save(barbearia);
    }

    public Barbearia alterarEndereco(Barbearia barbearia, String rua , String numeroRua) {
        Barbearia altEndereco = barbeariaRepository.findybyBarbeariaAndRuaAndNumeroRua(barbearia,rua,numeroRua);

        if (Objects.isNull(altEndereco)) {
            throw new RuntimeException("Endereço não encontrado para a barbearia informada.");
        }
        barbearia.setRua(altEndereco.getRua());
        barbearia.setNumeroRua(altEndereco.getNumeroRua());
        return barbeariaRepository.save(barbearia);
    }

    //      FAZER: Metodo de alterar (Proprietario)
}

