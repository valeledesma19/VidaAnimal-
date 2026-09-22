package com.vidaanimal.vidaanimal_backend.repository;

import com.vidaanimal.vidaanimal_backend.entity.EstadoTurno;
import com.vidaanimal.vidaanimal_backend.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TurnoRepository extends JpaRepository<Turno, Integer> {

    Optional<Turno> findByFechaAndHoraAndEstado(LocalDate fecha, LocalTime hora, EstadoTurno estado);

    List<Turno> findByFechaAndEstado(LocalDate fecha, EstadoTurno estado);

    List<Turno> findByMascotaId(Integer mascotaId);

    List<Turno> findByMascota_Cliente_Id(Integer clienteId);
}