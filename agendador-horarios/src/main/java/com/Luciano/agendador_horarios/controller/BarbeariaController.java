package com.Luciano.agendador_horarios.controller;


import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.service.BarbeariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/barbearias")
@RequiredArgsConstructor
public class BarbeariaController {

    private final BarbeariaService barbeariaService;

    @PostMapping
    public ResponseEntity<Barbearia> salvarBarbearia(@RequestBody Barbearia barbearia) {
        return ResponseEntity.accepted().body(barbeariaService.salvarBarbearia(barbearia));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarBarbearia(@RequestBody Barbearia barbearia, @RequestParam String nomeBarbearia) {

        barbeariaService.deletarBarbearia(nomeBarbearia);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Barbearia>> buscarBarbearia
            (@RequestParam String nomeBarbearia, @RequestParam long idBarbearia, @RequestParam String rua) {
        return ResponseEntity.ok().body(barbeariaService.buscarBarbearia(nomeBarbearia,idBarbearia, rua));
    }

    @PutMapping
    public ResponseEntity<Barbearia> alterarNomeBarbearia(@RequestBody Barbearia barbearia,
                                                          @RequestParam String nomeBarbearia) {
        return ResponseEntity.accepted().body(barbeariaService.alterarNomeBarbearia(barbearia, nomeBarbearia));
    }
    //      Fazer metodo de alterar (Horarios de funcionamento)
    @PutMapping
    public ResponseEntity<Barbearia> alterarHorariosFun(@RequestBody Barbearia barbearia,
                                                        @RequestParam LocalTime horarioAbertura,
                                                        @RequestParam LocalTime horarioFechamento) {

        return ResponseEntity.accepted().body(barbeariaService.alterarHorariosFun(barbearia, horarioAbertura,horarioFechamento));
    }

    //     FAZER: Metodo de alterar (Telefone)

    //      FAZER: Metodo de alterar (Endereço)

    //      FAZER: Metodo de alterar (Proprietario)
}
 
