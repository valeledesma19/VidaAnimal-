package com.vidaanimal.vidaanimal_backend.controller;

import com.vidaanimal.vidaanimal_backend.dto.VacunaResponse;
import com.vidaanimal.vidaanimal_backend.entity.Vacuna;
import com.vidaanimal.vidaanimal_backend.service.VacunaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vacunas")
public class VacunaController {

    private final VacunaService vacunaService;

    public VacunaController(VacunaService vacunaService) {
        this.vacunaService = vacunaService;
    }

    @GetMapping("/alertas")
    public List<VacunaResponse> alertas() {
        return vacunaService.buscarProximasAVencer().stream()
                .map(v -> new VacunaResponse(v.getId(), v.getMascota().getId(), v.getMascota().getNombre(),
                        v.getNombre(), v.getFechaAplicacion(), v.getDuracionMeses(), v.getFechaVencimiento()))
                .toList();
    }
}