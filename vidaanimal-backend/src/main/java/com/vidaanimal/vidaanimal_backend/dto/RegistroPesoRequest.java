package com.vidaanimal.vidaanimal_backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RegistroPesoRequest {

    @NotNull @Positive
    private BigDecimal valorKg;

    @NotNull
    private LocalDate fecha;

    public BigDecimal getValorKg() { return valorKg; }
    public void setValorKg(BigDecimal valorKg) { this.valorKg = valorKg; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}