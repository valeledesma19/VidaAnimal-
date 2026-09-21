package com.vidaanimal.vidaanimal_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "contrasenia_hash", nullable = false)
    private String contraseñaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseñaHash() {
        return contraseñaHash;
    }

    public void setContraseñaHash(String contraseñaHash) {
        this.contraseñaHash = contraseñaHash;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}