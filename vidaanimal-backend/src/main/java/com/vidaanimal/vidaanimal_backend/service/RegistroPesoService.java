package com.vidaanimal.vidaanimal_backend.service;

import com.vidaanimal.vidaanimal_backend.entity.RegistroPeso;
import com.vidaanimal.vidaanimal_backend.repository.RegistroPesoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroPesoService {

    private final RegistroPesoRepository registroPesoRepository;

    public RegistroPesoService(RegistroPesoRepository registroPesoRepository) {
        this.registroPesoRepository = registroPesoRepository;
    }

    public RegistroPeso registrar(RegistroPeso registroPeso) {
        return registroPesoRepository.save(registroPeso);
    }

    public List<RegistroPeso> buscarPorMascota(Integer mascotaId) {
        return registroPesoRepository.findByMascotaIdOrderByFechaDesc(mascotaId);
    }
}