package cl.kibernum.miprimerspringboot.repository;

import cl.kibernum.miprimerspringboot.bl.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * REPOSITORIO DE INSTRUCTORES
 * ────────────────────────────
 * Accede a la tabla "instructores" (con JOIN automático a "personas" por la herencia JOINED).
 * Todos los métodos CRUD básicos son heredados de JpaRepository.
 */
@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
}
