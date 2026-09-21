package com.vidaanimal.vidaanimal_backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "registro_peso")
public class RegistroPeso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @Column(name = "valor_kg", nullable = false)
    private BigDecimal valorKg;

    @Column(nullable = false)
    private LocalDate fecha;

    public RegistroPeso() {
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

    public BigDecimal getValorKg() {
        return valorKg;
    }

    public void setValorKg(BigDecimal valorKg) {
        this.valorKg = valorKg;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}