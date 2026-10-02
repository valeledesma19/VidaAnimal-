package com.vidaanimal.vidaanimal_backend.dto;

public class MascotaBusquedaResponse {

    private Integer id;
    private String nombre;
    private String raza;
    private Integer clienteId;
    private String clienteNombreCompleto;

    public MascotaBusquedaResponse(Integer id, String nombre, String raza,
                                   Integer clienteId, String clienteNombreCompleto) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.clienteId = clienteId;
        this.clienteNombreCompleto = clienteNombreCompleto;
    }

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public String getRaza() { return raza; }
    public Integer getClienteId() { return clienteId; }
    public String getClienteNombreCompleto() { return clienteNombreCompleto; }
}