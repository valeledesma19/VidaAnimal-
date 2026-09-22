package com.vidaanimal.vidaanimal_backend.dto;

import com.vidaanimal.vidaanimal_backend.entity.TamañoMascota;

import java.time.LocalDate;

public class MascotaResponse {

    private Integer id;
    private Integer clienteId;
    private String nombre;
    private String raza;
    private TamañoMascota tamaño;
    private LocalDate fechaNacimiento;

    public MascotaResponse(Integer id, Integer clienteId, String nombre, String raza,
                           TamañoMascota tamaño, LocalDate fechaNacimiento) {
        this.id = id;
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.raza = raza;
        this.tamaño = tamaño;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getId() { return id; }
    public Integer getClienteId() { return clienteId; }
    public String getNombre() { return nombre; }
    public String getRaza() { return raza; }
    public TamañoMascota getTamaño() { return tamaño; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
}