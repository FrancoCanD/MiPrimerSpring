package cl.kibernum.miprimerspringboot.repository;

import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * REPOSITORIO DE ESTUDIANTES
 * ───────────────────────────
 * Accede a la tabla "estudiantes" (con JOIN automático a "personas" por la herencia JOINED).
 * Los métodos básicos son heredados de JpaRepository.
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    /**
     * CONSULTA DERIVADA: buscar estudiante por RUT
     * ─────────────────────────────────────────────
     * Spring Data lee el nombre del método y genera el SQL:
     *   SELECT * FROM personas p JOIN estudiantes e ON p.id = e.persona_id WHERE p.rut = ?
     *
     * Retorna Optional<Estudiante> porque el RUT puede no existir.
     * Usado por EstudianteController para mostrar perfil y asistencias.
     */
    Optional<Estudiante> findByRut(String rut);
}
