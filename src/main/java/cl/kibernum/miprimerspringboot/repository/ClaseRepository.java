package cl.kibernum.miprimerspringboot.repository;

import cl.kibernum.miprimerspringboot.bl.entity.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * REPOSITORIO DE CLASES
 * ──────────────────────
 * Provee acceso a la tabla "clases" de la base de datos.
 * Hereda de JpaRepository los métodos CRUD básicos:
 *   - findAll()     → lista todas las clases
 *   - findById(id)  → busca una clase por ID
 *   - save(clase)   → crea o actualiza una clase
 *   - deleteById(id)→ elimina una clase por ID
 */
@Repository
public interface ClaseRepository extends JpaRepository<Clase, Integer> {
}
