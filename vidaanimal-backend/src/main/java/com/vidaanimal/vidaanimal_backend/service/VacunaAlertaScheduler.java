package com.vidaanimal.vidaanimal_backend.service;

import com.vidaanimal.vidaanimal_backend.entity.Vacuna;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VacunaAlertaScheduler {

    private final VacunaService vacunaService;
    private final EmailService emailService;

    public VacunaAlertaScheduler(VacunaService vacunaService, EmailService emailService) {
        this.vacunaService = vacunaService;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void enviarAlertasDiarias() {
        for (Vacuna vacuna : vacunaService.buscarProximasAVencer()) {
            emailService.enviarAlertaVacunaProximaAVencer(vacuna);
        }
    }
}