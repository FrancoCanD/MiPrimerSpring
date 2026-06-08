package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * INTERFAZ DE NEGOCIO PARA EL SERVICIO DE USUARIOS
 * ──────────────────────────────────────────────────
 * Una interfaz define el CONTRATO: dice QUÉ operaciones existen,
 * pero no CÓMO están programadas.
 *
 * La implementación real está en UsuarioServiceImpl.
 *
 * Ventaja: si en el futuro queremos cambiar cómo se guardan los usuarios
 * (por ejemplo, pasar a MongoDB), solo cambiamos la implementación sin
 * tocar los controladores que usan esta interfaz.
 *
 * NOTA: Esta interfaz es diferente a la de Spring Security (UserDetailsService).
 * Esta es para operaciones de negocio (CRUD + vínculos con personas).
 */
public interface UsuarioDetailsService {

    /**
     * Retorna la lista completa de usuarios registrados en el sistema.
     */
    List<Usuario> listarUsuarios();

    /**
     * Busca un usuario por su ID.
     * Retorna Optional para forzar al que llame a manejar el caso en que no exista.
     */
    Optional<Usuario> usuarioPorId(Integer id);

    /**
     * Crea o actualiza un usuario en la base de datos.
     * Retorna el objeto guardado (con el ID generado si es nuevo).
     */
    Usuario crearUsuario(Usuario usuario);

    /**
     * Elimina un usuario según su ID.
     */
    void borrarUsuario(Integer id);

    /**
     * Asocia una Persona existente (Estudiante o Instructor) a una cuenta de Usuario.
     * La persona queda identificable a través del campo persona_id en la tabla usuarios.
     *
     * @param usuarioId ID del usuario al que se le asignará la persona
     * @param personaId ID de la persona (de la tabla personas) a vincular
     */
    void vincularPersona(Integer usuarioId, Integer personaId);

    /**
     * Elimina la asociación entre un usuario y su persona vinculada.
     * El usuario sigue existiendo, pero persona_id quedará en NULL en la BD.
     *
     * @param usuarioId ID del usuario al que se le quitará la persona
     */
    void desvincularPersona(Integer usuarioId);
}
