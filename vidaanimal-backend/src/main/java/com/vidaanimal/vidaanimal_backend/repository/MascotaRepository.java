package com.vidaanimal.vidaanimal_backend.repository;

import com.vidaanimal.vidaanimal_backend.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {

    List<Mascota> findByClienteId(Integer clienteId);
}