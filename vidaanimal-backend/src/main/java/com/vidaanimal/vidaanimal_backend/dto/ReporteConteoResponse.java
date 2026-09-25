package com.vidaanimal.vidaanimal_backend.dto;

public class ReporteConteoResponse {

    private String etiqueta;
    private Long cantidad;

    public ReporteConteoResponse(String etiqueta, Long cantidad) {
        this.etiqueta = etiqueta;
        this.cantidad = cantidad;
    }

    public String getEtiqueta() { return etiqueta; }
    public Long getCantidad() { return cantidad; }
}