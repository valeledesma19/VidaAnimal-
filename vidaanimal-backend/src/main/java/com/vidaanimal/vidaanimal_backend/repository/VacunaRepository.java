package com.vidaanimal.vidaanimal_backend.repository;

import com.vidaanimal.vidaanimal_backend.entity.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VacunaRepository extends JpaRepository<Vacuna, Integer> {

    List<Vacuna> findByMascotaId(Integer mascotaId);

    @Query(value = """
        SELECT * FROM vacuna v
        WHERE (v.fecha_aplicacion + (v.duracion_meses || ' months')::interval)
              BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '30 days'
        """, nativeQuery = true)
    List<Vacuna> findProximasAVencer();

    @Query(value = """
        SELECT nombre, COUNT(*) AS cantidad
        FROM vacuna
        GROUP BY nombre
        ORDER BY cantidad DESC
        """, nativeQuery = true)
    List<Object[]> contarVacunasMasAplicadas();
}