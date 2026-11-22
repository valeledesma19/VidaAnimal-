package com.vidaanimal.vidaanimal_backend.service;

import com.vidaanimal.vidaanimal_backend.entity.Vacuna;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class VacunaAlertaScheduler {

    private final VacunaService vacunaService;
    private final EmailService emailService;

    public VacunaAlertaScheduler(VacunaService vacunaService, EmailService emailService) {
        this.vacunaService = vacunaService;
        this.emailService = emailService;
    }


    @Scheduled(cron = "0 0 8 * * *")
    @Transactional(readOnly = true)
    public void enviarRecordatorios() {
        for (Vacuna vacuna : vacunaService.buscarParaRecordatorio()) {
            try {
                emailService.enviarAlertaVacunaProximaAVencer(vacuna);
            } catch (Exception e) {
                System.err.println("No se pudo enviar el recordatorio de la vacuna " + vacuna.getId() + ": " + e.getMessage());
            }
        }
    }
}