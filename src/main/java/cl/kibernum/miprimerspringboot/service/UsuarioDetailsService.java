package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Interface del servicio de Usuario
 * contiene los metodos abstractos del Usuario
 */
public interface UsuarioDetailsService {
    /**
     * Lista todos los usuarios existentes en la BD
     * @return Lista de Usuarios
     */
    List<Usuario> listarUsuarios();

    /**
     * Buscar usuario por ID
     * @param id
     * @return usuario según id
     */

    Optional<Usuario> usuarioPorId(Integer id);

    /**
     * Crear un nuevo usuario
     * @param usuario (Objeto de tipo usuario)
     * @return 1
     */
    Usuario crearUsuario(Usuario usuario);

    /**
     * Borra un usuario según id
     * @param id del usuario a borrar
     */
    void borrarUsuario(Integer id);
}
