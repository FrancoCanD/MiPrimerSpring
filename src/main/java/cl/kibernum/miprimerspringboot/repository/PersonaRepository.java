package cl.kibernum.miprimerspringboot.repository;

import cl.kibernum.miprimerspringboot.bl.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * REPOSITORIO DE PERSONAS
 * ────────────────────────
 * Permite consultar la tabla "personas" que es la raíz de la jerarquía
 * de herencia Persona → Estudiante / Instructor.
 *
 * Aunque Persona es una clase abstracta en Java (no se pueden crear objetos
 * de Persona directamente), JPA sí puede consultarla porque está mapeada
 * a la tabla "personas" con @Inheritance(strategy = InheritanceType.JOINED).
 *
 * Al llamar a findAll(), JPA hace un JOIN entre "personas" y las tablas hijas
 * y retorna instancias del tipo concreto correcto: Estudiante o Instructor.
 *
 * Esto nos permite obtener todas las personas en una sola consulta, sin
 * tener que llamar por separado a EstudianteRepository e InstructorRepository.
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    // JpaRepository provee todos los métodos necesarios (findAll, findById, etc.)
    // No se necesitan métodos adicionales por ahora.
}
