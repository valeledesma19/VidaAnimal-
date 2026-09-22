package com.vidaanimal.vidaanimal_backend.service;

import com.vidaanimal.vidaanimal_backend.entity.Vacuna;
import com.vidaanimal.vidaanimal_backend.exception.BusinessException;
import com.vidaanimal.vidaanimal_backend.repository.VacunaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacunaService {

    private final VacunaRepository vacunaRepository;

    public VacunaService(VacunaRepository vacunaRepository) {
        this.vacunaRepository = vacunaRepository;
    }

    public Vacuna registrar(Vacuna vacuna) {
        return vacunaRepository.save(vacuna);
    }

    public List<Vacuna> buscarPorMascota(Integer mascotaId) {
        return vacunaRepository.findByMascotaId(mascotaId);
    }

    public List<Vacuna> buscarProximasAVencer() {
        return vacunaRepository.findProximasAVencer();
    }

    public Vacuna buscarPorId(Integer id) {
        return vacunaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Vacuna no encontrada"));
    }
}