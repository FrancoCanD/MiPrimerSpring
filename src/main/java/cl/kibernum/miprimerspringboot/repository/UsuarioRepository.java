package cl.kibernum.miprimerspringboot.repository;

import cl.kibernum.miprimerspringboot.bl.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * REPOSITORIO DE USUARIOS
 * ────────────────────────
 * Un repositorio en Spring Data JPA es la capa que habla directamente con la BD.
 * Al extender JpaRepository, Spring genera automáticamente los métodos básicos:
 *   - findAll()       → SELECT * FROM usuarios
 *   - findById(id)    → SELECT * FROM usuarios WHERE id = ?
 *   - save(usuario)   → INSERT o UPDATE según corresponda
 *   - deleteById(id)  → DELETE FROM usuarios WHERE id = ?
 *   - count()         → SELECT COUNT(*) FROM usuarios
 *
 * Los parámetros de JpaRepository<Usuario, Integer> significan:
 *   - Usuario  → la entidad que maneja este repositorio
 *   - Integer  → el tipo del campo @Id (la clave primaria)
 *
 * @Repository le dice a Spring que este es un componente de acceso a datos.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * CONSULTA DERIVADA: buscar usuario por username
     * ────────────────────────────────────────────────
     * Spring Data JPA genera el SQL automáticamente leyendo el nombre del método.
     * "findBy" + "Username" → WHERE username = ?
     *
     * Retorna Optional<Usuario> porque el usuario puede no existir.
     * Optional obliga al código que llama a manejar explícitamente ese caso,
     * evitando NullPointerException accidentales.
     *
     * Uso típico:
     *   usuarioRepository.findByUsername("admin")
     *       .orElseThrow(() -> new RuntimeException("No existe"));
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * CONSULTA DERIVADA: buscar usuario por el ID de su persona vinculada
     * ──────────────────────────────────────────────────────────────────
     * "findBy" + "Persona" + "Id" → WHERE persona_id = ?
     * (Spring navega la relación @OneToOne para acceder al campo id de Persona)
     *
     * Útil para verificar si una persona ya tiene una cuenta de usuario.
     */
    Optional<Usuario> findByPersonaId(Integer personaId);

    /**
     * CONSULTA DE EXISTENCIA: verificar si una persona ya tiene usuario
     * ─────────────────────────────────────────────────────────────────
     * "existsBy" + "Persona" + "Id" → SELECT COUNT(*) > 0 WHERE persona_id = ?
     *
     * Más eficiente que findByPersonaId() cuando solo necesitamos saber
     * si existe (true/false), sin cargar el objeto completo.
     *
     * Se usa en AdminController para filtrar personas disponibles para vincular.
     */
    boolean existsByPersonaId(Integer personaId);
}
