package com.vidaanimal.vidaanimal_backend.dto;

import com.vidaanimal.vidaanimal_backend.entity.EstadoTurno;

import java.time.LocalDate;
import java.time.LocalTime;

public class TurnoResponse {

    private Integer id;
    private Integer mascotaId;
    private String mascotaNombre;
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private EstadoTurno estado;

    public TurnoResponse(Integer id, Integer mascotaId, String mascotaNombre,
                         LocalDate fecha, LocalTime hora, String motivo, EstadoTurno estado) {
        this.id = id;
        this.mascotaId = mascotaId;
        this.mascotaNombre = mascotaNombre;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
    }

    public Integer getId() { return id; }
    public Integer getMascotaId() { return mascotaId; }
    public String getMascotaNombre() { return mascotaNombre; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public String getMotivo() { return motivo; }
    public EstadoTurno getEstado() { return estado; }
}