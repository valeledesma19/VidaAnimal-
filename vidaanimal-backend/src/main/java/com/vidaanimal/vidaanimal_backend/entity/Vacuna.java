package com.vidaanimal.vidaanimal_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vacuna")
public class Vacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "fecha_aplicacion", nullable = false)
    private LocalDate fechaAplicacion;

    @Column(name = "duracion_meses", nullable = false)
    private Integer duracionMeses;

    public Vacuna() {
    }

    public Integer getId() {
        return id;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDate fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public Integer getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(Integer duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    @Transient
    public LocalDate getFechaVencimiento() {
        return fechaAplicacion.plusMonths(duracionMeses);
    }
}