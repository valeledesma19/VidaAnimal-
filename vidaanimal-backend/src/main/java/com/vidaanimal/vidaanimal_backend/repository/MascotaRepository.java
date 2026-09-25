package com.vidaanimal.vidaanimal_backend.repository;

import com.vidaanimal.vidaanimal_backend.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {

    List<Mascota> findByClienteId(Integer clienteId);

    @Query(value = """
        SELECT TO_CHAR(fecha_alta, 'YYYY-MM') AS mes, COUNT(*) AS cantidad
        FROM mascota
        GROUP BY mes
        ORDER BY mes
        """, nativeQuery = true)
    List<Object[]> contarMascotasNuevasPorMes();
}