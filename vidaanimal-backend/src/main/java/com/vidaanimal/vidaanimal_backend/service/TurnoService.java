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
import java.util.List;

@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public Turno crear(Turno turno) {
        validarDisponibilidad(turno.getFecha(), turno.getHora(), null);
        turno.setEstado(EstadoTurno.CONFIRMADO);
        return turnoRepository.save(turno);
    }

    public Turno editar(Integer id, Turno datosNuevos, boolean esCliente) {
        Turno existente = buscarPorId(id);

        if (esCliente) {
            validarPropietario(existente, datosNuevos);
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

    public void cancelar(Integer id, boolean esCliente) {
        Turno existente = buscarPorId(id);

        if (esCliente) {
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
        return turnoRepository.findByFecha(fecha);
    }

    public List<Turno> buscarPorCliente(Integer clienteId) {
        return turnoRepository.findByMascota_Cliente_Id(clienteId);
    }

    private void validarDisponibilidad(LocalDate fecha, LocalTime hora, Integer idAIgnorar) {
        turnoRepository.findByFechaAndHora(fecha, hora)
                .filter(t -> !t.getId().equals(idAIgnorar))
                .ifPresent(t -> {
                    throw new BusinessException("Ya existe un turno en ese horario");
                });
    }

    private void validarAntiguedad(Turno turno) {
        LocalDateTime fechaHoraTurno = LocalDateTime.of(turno.getFecha(), turno.getHora());
        long horasRestantes = ChronoUnit.HOURS.between(LocalDateTime.now(), fechaHoraTurno);
        if (horasRestantes < 24) {
            throw new BusinessException("Solo podés editar o cancelar turnos con 24hs de anticipación o más");
        }
    }

    private void validarPropietario(Turno turno, Turno datosNuevos) {

    }
}