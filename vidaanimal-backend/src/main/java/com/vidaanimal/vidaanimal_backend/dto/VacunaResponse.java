package com.vidaanimal.vidaanimal_backend.dto;

import java.time.LocalDate;

public class VacunaResponse {

    private Integer id;
    private Integer mascotaId;
    private String mascotaNombre;
    private String nombre;
    private LocalDate fechaAplicacion;
    private Integer duracionMeses;
    private LocalDate fechaVencimiento;

    public VacunaResponse(Integer id, Integer mascotaId, String mascotaNombre, String nombre,
                          LocalDate fechaAplicacion, Integer duracionMeses, LocalDate fechaVencimiento) {
        this.id = id;
        this.mascotaId = mascotaId;
        this.mascotaNombre = mascotaNombre;
        this.nombre = nombre;
        this.fechaAplicacion = fechaAplicacion;
        this.duracionMeses = duracionMeses;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getId() { return id; }
    public Integer getMascotaId() { return mascotaId; }
    public String getMascotaNombre() { return mascotaNombre; }
    public String getNombre() { return nombre; }
    public LocalDate getFechaAplicacion() { return fechaAplicacion; }
    public Integer getDuracionMeses() { return duracionMeses; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
}