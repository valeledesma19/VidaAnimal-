package com.vidaanimal.vidaanimal_backend.service;

import com.vidaanimal.vidaanimal_backend.dto.ReporteConteoResponse;
import com.vidaanimal.vidaanimal_backend.repository.MascotaRepository;
import com.vidaanimal.vidaanimal_backend.repository.TurnoRepository;
import com.vidaanimal.vidaanimal_backend.repository.VacunaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {

    private final TurnoRepository turnoRepository;
    private final VacunaRepository vacunaRepository;
    private final MascotaRepository mascotaRepository;

    public ReporteService(TurnoRepository turnoRepository, VacunaRepository vacunaRepository,
                          MascotaRepository mascotaRepository) {
        this.turnoRepository = turnoRepository;
        this.vacunaRepository = vacunaRepository;
        this.mascotaRepository = mascotaRepository;
    }

    public List<ReporteConteoResponse> turnosPorMes() {
        return mapear(turnoRepository.contarTurnosPorMes());
    }

    public List<ReporteConteoResponse> vacunasMasAplicadas() {
        return mapear(vacunaRepository.contarVacunasMasAplicadas());
    }

    public List<ReporteConteoResponse> mascotasNuevasPorMes() {
        return mapear(mascotaRepository.contarMascotasNuevasPorMes());
    }

    private List<ReporteConteoResponse> mapear(List<Object[]> filas) {
        return filas.stream()
                .map(fila -> new ReporteConteoResponse((String) fila[0], ((Number) fila[1]).longValue()))
                .toList();
    }
}