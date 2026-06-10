package com.Luciano.agendador_horarios.controller;

import com.Luciano.agendador_horarios.DTO.DashboardResumoResponseDTO;
import com.Luciano.agendador_horarios.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResumoResponseDTO> totalFuncionariosEServicos() {
       var total = dashboardService.resumo();
        return ResponseEntity.ok(total);
    }
}
