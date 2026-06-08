package cl.kibernum.miprimerspringboot.repository;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * REPOSITORIO DE ASISTENCIAS
 * ───────────────────────────
 * Al extender JpaRepository<Asistencia, Integer>, Spring genera automáticamente:
 *   - findAll()        → SELECT * FROM asistencias (con JOINs a estudiante, instructor, clase)
 *   - findById(id)     → SELECT ... WHERE id = ?
 *   - save(asistencia) → INSERT o UPDATE
 *   - deleteById(id)   → DELETE WHERE id = ?
 *   - count()          → SELECT COUNT(*)
 *
 * No se necesitan métodos adicionales por ahora, los heredados son suficientes.
 */
@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {
}
