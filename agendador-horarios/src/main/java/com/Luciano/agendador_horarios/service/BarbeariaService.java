package com.Luciano.agendador_horarios.service;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
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
        Barbearia barbeariaExistente = barbeariaRepository.findByNomeBarbeariaAndRuaAndNumeroRua(
                barbearia.getNomeBarbearia(),
                barbearia.getRua(),
                barbearia.getNumeroRua()
        );

        if (Objects.nonNull(barbeariaExistente)) {
            throw new RuntimeException("Barbearia já está cadastrada.");
        }

        return barbeariaRepository.save(barbearia);
    }

    public void deletarBarbearia(String nomeBarbearia) {
        barbeariaRepository.deleteByNomeBarbearia(nomeBarbearia);
    }

    public List<Barbearia> buscarBarbearia(String nomeBarbearia) {
        return barbeariaRepository.findByNomeBarbearia(nomeBarbearia);
    }

    public Barbearia alterarBarbearia(Barbearia barbearia, String nomeBarbearia, String rua, int numeroRua) {
        Barbearia barbeariaExistente = barbeariaRepository.findByNomeBarbeariaAndRuaAndNumeroRua(
                nomeBarbearia,
                rua,
                numeroRua
        );

        if (Objects.isNull(barbeariaExistente)) {
            throw new RuntimeException("Barbearia não encontrada.");
        }

        barbearia.setIdBarbearia(barbeariaExistente.getIdBarbearia());
        return barbeariaRepository.save(barbearia);
    }
}
