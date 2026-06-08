package cl.kibernum.miprimerspringboot.repository;

import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * REPOSITORIO DE GRADOS
 * ──────────────────────
 * Accede a la tabla "grados".
 * Todos los métodos necesarios son heredados de JpaRepository.
 * No se requieren consultas personalizadas para esta entidad.
 */
@Repository
public interface GradoRepository extends JpaRepository<Grado, Integer> {
}
