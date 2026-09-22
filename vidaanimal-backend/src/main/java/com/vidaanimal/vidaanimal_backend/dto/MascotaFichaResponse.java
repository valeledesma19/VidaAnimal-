package com.vidaanimal.vidaanimal_backend.dto;

import java.util.List;

public class MascotaFichaResponse {

    private MascotaResponse mascota;
    private List<VacunaResponse> vacunas;
    private List<RegistroPesoResponse> pesos;

    public MascotaFichaResponse(MascotaResponse mascota, List<VacunaResponse> vacunas, List<RegistroPesoResponse> pesos) {
        this.mascota = mascota;
        this.vacunas = vacunas;
        this.pesos = pesos;
    }

    public MascotaResponse getMascota() { return mascota; }
    public List<VacunaResponse> getVacunas() { return vacunas; }
    public List<RegistroPesoResponse> getPesos() { return pesos; }
}