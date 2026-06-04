package cl.kibernum.miprimerspringboot.repository;

import cl.kibernum.miprimerspringboot.bl.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Usuario
 * JPA repository entrega automáticamente métodos como:
 * -findAll()
 * -findById
 * -save()
 * -deleteById()
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
