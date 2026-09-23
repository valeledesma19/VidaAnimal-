package com.vidaanimal.vidaanimal_backend.service;

import com.vidaanimal.vidaanimal_backend.entity.Turno;
import com.vidaanimal.vidaanimal_backend.entity.Vacuna;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarConfirmacionTurno(Turno turno) {
        String destinatario = turno.getMascota().getCliente().getUsuario().getEmail();
        String nombreMascota = turno.getMascota().getNombre();

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("VidaAnimal - Turno confirmado");
        mensaje.setText("""
                Hola,

                Se confirmó un turno para %s el día %s a las %s.

                Motivo: %s

                Saludos,
                VidaAnimal
                """.formatted(
                nombreMascota,
                turno.getFecha().format(FORMATO_FECHA),
                turno.getHora(),
                turno.getMotivo() != null ? turno.getMotivo() : "-"
        ));

        mailSender.send(mensaje);
    }

    public void enviarAlertaVacunaProximaAVencer(Vacuna vacuna) {
        String destinatario = vacuna.getMascota().getCliente().getUsuario().getEmail();
        String nombreMascota = vacuna.getMascota().getNombre();

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("VidaAnimal - Vacuna próxima a vencer");
        mensaje.setText("""
                Hola,

                La vacuna "%s" de %s vence el día %s.

                Te recomendamos coordinar un turno para renovarla.

                Saludos,
                VidaAnimal
                """.formatted(
                vacuna.getNombre(),
                nombreMascota,
                vacuna.getFechaVencimiento().format(FORMATO_FECHA)
        ));

        mailSender.send(mensaje);
    }
}