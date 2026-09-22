package com.vidaanimal.vidaanimal_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RegistroPesoResponse {

    private Integer id;
    private BigDecimal valorKg;
    private LocalDate fecha;

    public RegistroPesoResponse(Integer id, BigDecimal valorKg, LocalDate fecha) {
        this.id = id;
        this.valorKg = valorKg;
        this.fecha = fecha;
    }

    public Integer getId() { return id; }
    public BigDecimal getValorKg() { return valorKg; }
    public LocalDate getFecha() { return fecha; }
}