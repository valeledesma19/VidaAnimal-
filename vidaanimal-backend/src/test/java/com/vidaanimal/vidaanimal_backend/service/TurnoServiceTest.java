package com.vidaanimal.vidaanimal_backend.service;

import com.vidaanimal.vidaanimal_backend.entity.*;
import com.vidaanimal.vidaanimal_backend.exception.BusinessException;
import com.vidaanimal.vidaanimal_backend.repository.TurnoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private TurnoService turnoService;

    private Turno turnoDeCliente(Integer clienteId, LocalDateTime cuando) {
        Cliente cliente = new Cliente();
        Mascota mascota = new Mascota();
        mascota.setCliente(clienteConId(clienteId));

        Turno turno = new Turno();
        turno.setMascota(mascota);
        turno.setFecha(cuando.toLocalDate());
        turno.setHora(cuando.toLocalTime());
        return turno;
    }

    private Cliente clienteConId(Integer id) {
        Cliente c = new Cliente();
        return c;
    }

    @Test
    void crear_turnoEnHorarioLibre_loConfirmaYMandaEmail() {
        Turno turno = new Turno();
        turno.setFecha(LocalDate.now().plusDays(5));
        turno.setHora(LocalTime.of(10, 0));

        when(turnoRepository.findByFechaAndHoraAndEstado(turno.getFecha(), turno.getHora(), EstadoTurno.CONFIRMADO))
                .thenReturn(Optional.empty());
        when(turnoRepository.save(any(Turno.class))).thenAnswer(inv -> inv.getArgument(0));

        Turno resultado = turnoService.crear(turno, null);

        assertEquals(EstadoTurno.CONFIRMADO, resultado.getEstado());
        verify(turnoRepository).save(turno);
        verify(emailService).enviarConfirmacionTurno(turno);
    }

    @Test
    void crear_horarioYaOcupado_lanzaBusinessException() {
        Turno turno = new Turno();
        turno.setFecha(LocalDate.now().plusDays(5));
        turno.setHora(LocalTime.of(10, 0));

        Turno existente = new Turno();
        existente.setFecha(turno.getFecha());
        existente.setHora(turno.getHora());

        when(turnoRepository.findByFechaAndHoraAndEstado(turno.getFecha(), turno.getHora(), EstadoTurno.CONFIRMADO))
                .thenReturn(Optional.of(existente));

        assertThrows(BusinessException.class, () -> turnoService.crear(turno, null));
        verify(turnoRepository, never()).save(any());
    }

    @Test
    void cancelar_empleado_sinRestriccionDe24hs_funcionaAunqueSeaMuyPronto() {
        Turno turno = new Turno();
        turno.setEstado(EstadoTurno.CONFIRMADO);
        turno.setFecha(LocalDate.now());
        turno.setHora(LocalTime.now().plusHours(2)); // falta menos de 24hs

        when(turnoRepository.findById(1)).thenReturn(Optional.of(turno));
        when(turnoRepository.save(any(Turno.class))).thenAnswer(inv -> inv.getArgument(0));

        assertDoesNotThrow(() -> turnoService.cancelar(1, null)); // null = empleado
        assertEquals(EstadoTurno.CANCELADO, turno.getEstado());
    }
}