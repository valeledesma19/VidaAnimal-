package com.vidaanimal.vidaanimal_backend.repository;

import com.vidaanimal.vidaanimal_backend.entity.RegistroPeso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroPesoRepository extends JpaRepository<RegistroPeso, Integer> {

    List<RegistroPeso> findByMascotaIdOrderByFechaDesc(Integer mascotaId);
}