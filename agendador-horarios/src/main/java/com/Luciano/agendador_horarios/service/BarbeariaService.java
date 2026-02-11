package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import com.Luciano.agendador_horarios.infrastructure.repository.BarbeariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    //     FAZER: Metodo de alterar (Telefone)

    //      FAZER: Metodo de alterar (Endereço)

    //      FAZER: Metodo de alterar (Proprietario)
}

