package cl.kibernum.miprimerspringboot.repository;

import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Grado
 * JPA repository entrega automáticamente métodos como:
 * -findAll()
 * -findById
 * -save()
 * -deleteById()
 */
@Repository
public interface GradoRepository extends JpaRepository<Grado, Integer> {
}
