package com.vidaanimal.vidaanimal_backend.service;

import com.vidaanimal.vidaanimal_backend.entity.Mascota;
import com.vidaanimal.vidaanimal_backend.exception.BusinessException;
import com.vidaanimal.vidaanimal_backend.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public Mascota guardar(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public Mascota buscarPorId(Integer id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Mascota no encontrada"));
    }

    public List<Mascota> buscarPorClienteId(Integer clienteId) {
        return mascotaRepository.findByClienteId(clienteId);
    }

    public void validarPropietario(Mascota mascota, Integer clienteId) {
        if (!mascota.getCliente().getId().equals(clienteId)) {
            throw new BusinessException("No tenés permiso sobre esta mascota");
        }
    }
}