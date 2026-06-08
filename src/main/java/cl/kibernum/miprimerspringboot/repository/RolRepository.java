package cl.kibernum.miprimerspringboot.repository;

import cl.kibernum.miprimerspringboot.bl.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * REPOSITORIO DE ROLES
 * ─────────────────────
 * Accede a la tabla "roles" que contiene los roles de seguridad del sistema.
 * Los roles disponibles son: ROL_ADMIN, ROL_INSTRUCTOR, ROL_ESTUDIANTE.
 *
 * No necesita @Repository explícito si extiende JpaRepository,
 * pero se mantiene por claridad y buenas prácticas.
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    /**
     * CONSULTA DERIVADA: buscar rol por su nombre exacto
     * ────────────────────────────────────────────────────
     * "findBy" + "Nombre" genera: SELECT * FROM roles WHERE nombre = ?
     *
     * Útil cuando se necesita asignar un rol a un usuario buscando por nombre,
     * por ejemplo: rolRepository.findByNombre("ROL_ADMIN")
     */
    Optional<Rol> findByNombre(String nombre);
}
