package cl.kibernum.miprimerspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

/**
 * Repositorio de Instructor
 * JPA repository entrega automáticamente métodos como:
 * -findAll()
 * -findById
 * -save()
 * -deleteById()
 */
@RestController

public interface InstructorRepository extends JpaRepository<Instrctor, Integer> {
}
