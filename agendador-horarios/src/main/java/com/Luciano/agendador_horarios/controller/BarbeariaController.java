package com.Luciano.agendador_horarios.controller;


import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.service.BarbeariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Barbearia>> buscarBarbearias
            (@RequestParam String nomeBarbearia, @RequestParam long idBarbearia, @RequestParam String rua) {
        return ResponseEntity.ok().body(barbeariaService.buscarBarbearia(nomeBarbearia, idBarbearia, rua));
    }
       //    Fazer >Alterar Barbearia


}

