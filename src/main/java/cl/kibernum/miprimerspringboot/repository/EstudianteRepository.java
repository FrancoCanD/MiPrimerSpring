package cl.kibernum.miprimerspringboot.repository;

import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de Estudiante
 * JPA repository entrega automáticamente métodos como:
 * -findAll()
 * -findById
 * -save()
 * -deleteById()
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    /**
     * Busca un estudiante por su RUT
     * Spring Data JPA genera la consulta automáticamente
     * @param rut del estudiante
     * @return Optional con el estudiante encontrado
     */
    Optional<Estudiante> findByRut(String rut);
}
