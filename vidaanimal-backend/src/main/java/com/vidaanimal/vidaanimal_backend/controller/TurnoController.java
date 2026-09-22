package com.vidaanimal.vidaanimal_backend.controller;

import com.vidaanimal.vidaanimal_backend.dto.TurnoRequest;
import com.vidaanimal.vidaanimal_backend.dto.TurnoResponse;
import com.vidaanimal.vidaanimal_backend.entity.*;
import com.vidaanimal.vidaanimal_backend.security.CustomUserDetails;
import com.vidaanimal.vidaanimal_backend.service.ClienteService;
import com.vidaanimal.vidaanimal_backend.service.MascotaService;
import com.vidaanimal.vidaanimal_backend.service.TurnoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    private final TurnoService turnoService;
    private final MascotaService mascotaService;
    private final ClienteService clienteService;

    public TurnoController(TurnoService turnoService, MascotaService mascotaService, ClienteService clienteService) {
        this.turnoService = turnoService;
        this.mascotaService = mascotaService;
        this.clienteService = clienteService;
    }

    @GetMapping("/disponibilidad")
    public List<LocalTime> disponibilidad(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return turnoService.listarDisponibilidad(fecha);
    }

    @GetMapping("/agenda")
    public List<TurnoResponse> agenda(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return turnoService.buscarPorFecha(fecha).stream().map(this::toResponse).toList();
    }

    @PostMapping
    public ResponseEntity<TurnoResponse> crear(@Valid @RequestBody TurnoRequest request,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        Usuario usuario = userDetails.getUsuario();
        Mascota mascota = mascotaService.buscarPorId(request.getMascotaId());

        Turno turno = new Turno();
        turno.setMascota(mascota);
        turno.setCreadoPor(usuario);
        turno.setFecha(request.getFecha());
        turno.setHora(request.getHora());
        turno.setMotivo(request.getMotivo());

        Integer clienteIdAutenticado = resolverClienteId(usuario);
        Turno creado = turnoService.crear(turno, clienteIdAutenticado);
        return ResponseEntity.ok(toResponse(creado));
    }

    @PutMapping("/{id}")
    public TurnoResponse editar(@PathVariable Integer id, @Valid @RequestBody TurnoRequest request,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        Usuario usuario = userDetails.getUsuario();

        Turno datosNuevos = new Turno();
        datosNuevos.setFecha(request.getFecha());
        datosNuevos.setHora(request.getHora());
        datosNuevos.setMotivo(request.getMotivo());

        Integer clienteIdAutenticado = resolverClienteId(usuario);
        Turno actualizado = turnoService.editar(id, datosNuevos, clienteIdAutenticado);
        return toResponse(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Integer id,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        Integer clienteIdAutenticado = resolverClienteId(userDetails.getUsuario());
        turnoService.cancelar(id, clienteIdAutenticado);
        return ResponseEntity.noContent().build();
    }

    private Integer resolverClienteId(Usuario usuario) {
        if (usuario.getRol() != Rol.CLIENTE) {
            return null;
        }
        return clienteService.buscarPorUsuarioId(usuario.getId()).getId();
    }

    private TurnoResponse toResponse(Turno turno) {
        return new TurnoResponse(
                turno.getId(),
                turno.getMascota().getId(),
                turno.getMascota().getNombre(),
                turno.getFecha(),
                turno.getHora(),
                turno.getMotivo(),
                turno.getEstado()
        );
    }
}