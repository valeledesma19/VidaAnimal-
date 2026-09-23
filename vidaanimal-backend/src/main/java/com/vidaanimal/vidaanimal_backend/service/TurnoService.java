package com.vidaanimal.vidaanimal_backend.service;

import com.vidaanimal.vidaanimal_backend.entity.EstadoTurno;
import com.vidaanimal.vidaanimal_backend.entity.Turno;
import com.vidaanimal.vidaanimal_backend.exception.BusinessException;
import com.vidaanimal.vidaanimal_backend.repository.TurnoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TurnoService {

    private static final LocalTime HORA_APERTURA = LocalTime.of(9, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(18, 0);
    private static final int DURACION_TURNO_MIN = 30;

    private final TurnoRepository turnoRepository;
    private final EmailService emailService;

    public TurnoService(TurnoRepository turnoRepository, EmailService emailService) {
        this.turnoRepository = turnoRepository;
        this.emailService = emailService;
    }

    public Turno crear(Turno turno, Integer clienteIdAutenticado) {
        if (clienteIdAutenticado != null) {
            validarPropietario(turno, clienteIdAutenticado);
        }
        validarDisponibilidad(turno.getFecha(), turno.getHora(), null);
        turno.setEstado(EstadoTurno.CONFIRMADO);
        Turno guardado = turnoRepository.save(turno);
        emailService.enviarConfirmacionTurno(guardado);
        return guardado;
    }

    public Turno editar(Integer id, Turno datosNuevos, Integer clienteIdAutenticado) {
        Turno existente = buscarPorId(id);

        if (clienteIdAutenticado != null) {
            validarPropietario(existente, clienteIdAutenticado);
            validarAntiguedad(existente);
        }

        if (!existente.getFecha().equals(datosNuevos.getFecha())
                || !existente.getHora().equals(datosNuevos.getHora())) {
            validarDisponibilidad(datosNuevos.getFecha(), datosNuevos.getHora(), id);
        }

        existente.setFecha(datosNuevos.getFecha());
        existente.setHora(datosNuevos.getHora());
        existente.setMotivo(datosNuevos.getMotivo());
        return turnoRepository.save(existente);
    }

    public void cancelar(Integer id, Integer clienteIdAutenticado) {
        Turno existente = buscarPorId(id);

        if (clienteIdAutenticado != null) {
            validarPropietario(existente, clienteIdAutenticado);
            validarAntiguedad(existente);
        }

        existente.setEstado(EstadoTurno.CANCELADO);
        turnoRepository.save(existente);
    }

    public Turno buscarPorId(Integer id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Turno no encontrado"));
    }

    public List<Turno> buscarPorFecha(LocalDate fecha) {
        return turnoRepository.findByFechaAndEstado(fecha, EstadoTurno.CONFIRMADO);
    }

    public List<Turno> buscarPorCliente(Integer clienteId) {
        return turnoRepository.findByMascota_Cliente_Id(clienteId);
    }

    public List<LocalTime> listarDisponibilidad(LocalDate fecha) {
        Set<LocalTime> ocupados = turnoRepository.findByFechaAndEstado(fecha, EstadoTurno.CONFIRMADO)
                .stream()
                .map(Turno::getHora)
                .collect(Collectors.toSet());

        List<LocalTime> libres = new ArrayList<>();
        LocalTime cursor = HORA_APERTURA;
        while (cursor.isBefore(HORA_CIERRE)) {
            if (!ocupados.contains(cursor)) {
                libres.add(cursor);
            }
            cursor = cursor.plusMinutes(DURACION_TURNO_MIN);
        }
        return libres;
    }

    private void validarDisponibilidad(LocalDate fecha, LocalTime hora, Integer idAIgnorar) {
        turnoRepository.findByFechaAndHoraAndEstado(fecha, hora, EstadoTurno.CONFIRMADO)
                .filter(t -> !t.getId().equals(idAIgnorar))
                .ifPresent(t -> {
                    throw new BusinessException("Ya existe un turno confirmado en ese horario");
                });
    }

    private void validarAntiguedad(Turno turno) {
        LocalDateTime fechaHoraTurno = LocalDateTime.of(turno.getFecha(), turno.getHora());
        long horasRestantes = ChronoUnit.HOURS.between(LocalDateTime.now(), fechaHoraTurno);
        if (horasRestantes < 24) {
            throw new BusinessException("Solo podés editar o cancelar turnos con 24hs de anticipación o más");
        }
    }

    private void validarPropietario(Turno turno, Integer clienteId) {
        if (!turno.getMascota().getCliente().getId().equals(clienteId)) {
            throw new BusinessException("No tenés permiso sobre este turno");
        }
    }
}