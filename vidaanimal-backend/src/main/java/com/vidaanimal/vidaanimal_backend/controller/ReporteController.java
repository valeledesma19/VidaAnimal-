package com.vidaanimal.vidaanimal_backend.controller;

import com.vidaanimal.vidaanimal_backend.dto.ReporteConteoResponse;
import com.vidaanimal.vidaanimal_backend.service.ReporteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/turnos-por-mes")
    public List<ReporteConteoResponse> turnosPorMes() {
        return reporteService.turnosPorMes();
    }

    @GetMapping("/vacunas-mas-aplicadas")
    public List<ReporteConteoResponse> vacunasMasAplicadas() {
        return reporteService.vacunasMasAplicadas();
    }

    @GetMapping("/mascotas-nuevas-por-mes")
    public List<ReporteConteoResponse> mascotasNuevasPorMes() {
        return reporteService.mascotasNuevasPorMes();
    }
}