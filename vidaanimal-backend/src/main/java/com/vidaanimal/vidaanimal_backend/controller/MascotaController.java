package com.vidaanimal.vidaanimal_backend.controller;

import com.vidaanimal.vidaanimal_backend.dto.*;
import com.vidaanimal.vidaanimal_backend.entity.*;
import com.vidaanimal.vidaanimal_backend.security.CustomUserDetails;
import com.vidaanimal.vidaanimal_backend.service.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;
    private final ClienteService clienteService;
    private final VacunaService vacunaService;
    private final RegistroPesoService registroPesoService;

    public MascotaController(MascotaService mascotaService, ClienteService clienteService,
                             VacunaService vacunaService, RegistroPesoService registroPesoService) {
        this.mascotaService = mascotaService;
        this.clienteService = clienteService;
        this.vacunaService = vacunaService;
        this.registroPesoService = registroPesoService;
    }

    @GetMapping("/{id}")
    public MascotaFichaResponse ficha(@PathVariable Integer id,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        Integer clienteIdAutenticado = resolverClienteId(userDetails.getUsuario());
        Mascota mascota = mascotaService.buscarPorIdValidandoAcceso(id, clienteIdAutenticado);

        List<VacunaResponse> vacunas = vacunaService.buscarPorMascota(id).stream()
                .map(this::toVacunaResponse).toList();

        List<RegistroPesoResponse> pesos = registroPesoService.buscarPorMascota(id).stream()
                .map(p -> new RegistroPesoResponse(p.getId(), p.getValorKg(), p.getFecha()))
                .toList();

        return new MascotaFichaResponse(toMascotaResponse(mascota), vacunas, pesos);
    }
    @GetMapping
    public List<MascotaResponse> listar(@RequestParam(required = false) Integer clienteId,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Usuario usuario = userDetails.getUsuario();
        Integer clienteIdAutenticado = resolverClienteId(usuario);

        Integer clienteIdConsulta = (clienteIdAutenticado != null) ? clienteIdAutenticado : clienteId;

        if (clienteIdConsulta == null) {
            throw new IllegalArgumentException("clienteId es obligatorio cuando consulta un empleado");
        }

        return mascotaService.buscarPorClienteId(clienteIdConsulta).stream()
                .map(this::toMascotaResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<MascotaResponse> crear(@Valid @RequestBody MascotaRequest request,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        Usuario usuario = userDetails.getUsuario();
        Integer clienteIdAutenticado = resolverClienteId(usuario);

        Integer clienteIdDestino = (clienteIdAutenticado != null)
                ? clienteIdAutenticado
                : request.getClienteId();

        if (clienteIdDestino == null) {
            throw new IllegalArgumentException("clienteId es obligatorio cuando el alta la hace un empleado");
        }

        Cliente cliente = clienteService.buscarPorId(clienteIdDestino);

        Mascota mascota = new Mascota();
        mascota.setCliente(cliente);
        mascota.setNombre(request.getNombre());
        mascota.setRaza(request.getRaza());
        mascota.setTamaño(request.getTamaño());
        mascota.setFechaNacimiento(request.getFechaNacimiento());

        Mascota creada = mascotaService.guardar(mascota);
        return ResponseEntity.ok(toMascotaResponse(creada));
    }

    @PostMapping("/{id}/vacunas")
    public ResponseEntity<VacunaResponse> registrarVacuna(@PathVariable Integer id,
                                                          @Valid @RequestBody VacunaRequest request) {
        Mascota mascota = mascotaService.buscarPorId(id);

        Vacuna vacuna = new Vacuna();
        vacuna.setMascota(mascota);
        vacuna.setNombre(request.getNombre());
        vacuna.setFechaAplicacion(request.getFechaAplicacion());
        vacuna.setDuracionMeses(request.getDuracionMeses());

        Vacuna guardada = vacunaService.registrar(vacuna);
        return ResponseEntity.ok(toVacunaResponse(guardada));
    }

    @PostMapping("/{id}/peso")
    public ResponseEntity<RegistroPesoResponse> registrarPeso(@PathVariable Integer id,
                                                              @Valid @RequestBody RegistroPesoRequest request) {
        Mascota mascota = mascotaService.buscarPorId(id);

        RegistroPeso registro = new RegistroPeso();
        registro.setMascota(mascota);
        registro.setValorKg(request.getValorKg());
        registro.setFecha(request.getFecha());

        RegistroPeso guardado = registroPesoService.registrar(registro);
        return ResponseEntity.ok(new RegistroPesoResponse(guardado.getId(), guardado.getValorKg(), guardado.getFecha()));
    }

    private Integer resolverClienteId(Usuario usuario) {
        if (usuario.getRol() != Rol.CLIENTE) {
            return null;
        }
        return clienteService.buscarPorUsuarioId(usuario.getId()).getId();
    }

    private MascotaResponse toMascotaResponse(Mascota m) {
        return new MascotaResponse(m.getId(), m.getCliente().getId(), m.getNombre(),
                m.getRaza(), m.getTamaño(), m.getFechaNacimiento());
    }

    private VacunaResponse toVacunaResponse(Vacuna v) {
        return new VacunaResponse(v.getId(), v.getMascota().getId(), v.getMascota().getNombre(),
                v.getNombre(), v.getFechaAplicacion(), v.getDuracionMeses(), v.getFechaVencimiento());
    }
}