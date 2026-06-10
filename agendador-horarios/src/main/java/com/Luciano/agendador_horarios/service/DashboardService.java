package com.Luciano.agendador_horarios.service;


import com.Luciano.agendador_horarios.DTO.DashboardResumoResponseDTO;
import com.Luciano.agendador_horarios.infrastructure.repository.FuncionarioRepository;
import com.Luciano.agendador_horarios.infrastructure.repository.ServicosRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ServicosRepository servicosRepository;
    private final FuncionarioRepository funcionarioRepository;


     public DashboardResumoResponseDTO resumo() {

         long totalFuncionarios = funcionarioRepository.count();
         long totalServicos = servicosRepository.count();

         DashboardResumoResponseDTO response =
                 new DashboardResumoResponseDTO();

         response.setTotalFuncionarios(totalFuncionarios);
         response.setTotalServicos(totalServicos);

         return response;
     }
}
