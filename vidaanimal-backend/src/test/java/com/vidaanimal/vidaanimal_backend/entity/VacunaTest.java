package com.vidaanimal.vidaanimal_backend.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VacunaTest {

    @Test
    void fechaVencimiento_seCalculaSumandoDuracionMeses() {
        Vacuna vacuna = new Vacuna();
        vacuna.setFechaAplicacion(LocalDate.of(2026, 1, 15));
        vacuna.setDuracionMeses(6);

        assertEquals(LocalDate.of(2026, 7, 15), vacuna.getFechaVencimiento());
    }
}