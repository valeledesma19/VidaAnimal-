package com.vidaanimal.vidaanimal_backend.dto;

import com.vidaanimal.vidaanimal_backend.entity.TamañoMascota;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MascotaRequest {

    // Solo lo completa el EMPLEADO cuando da de alta una mascota para un cliente puntual.
    // Si el que crea es un CLIENTE, este valor se ignora y se usa el suyo propio.
    private Integer clienteId;

    @NotBlank
    private String nombre;

    @NotBlank
    private String raza;

    @NotNull
    private TamañoMascota tamaño;

    @NotNull
    private LocalDate fechaNacimiento;

    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public TamañoMascota getTamaño() { return tamaño; }
    public void setTamaño(TamañoMascota tamaño) { this.tamaño = tamaño; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}