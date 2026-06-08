package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Usuario;
import cl.kibernum.miprimerspringboot.dto.request.UsuarioCreateRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.UsuarioResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

/**
 * INTERFAZ DE NEGOCIO PARA EL SERVICIO DE USUARIOS
 * ──────────────────────────────────────────────────
 * Define el CONTRATO de operaciones sobre usuarios.
 * La implementación está en UsuarioServiceImpl.
 *
 * NOTA: Esta interfaz es para lógica de negocio.
 * La interfaz de Spring Security (UserDetailsService) está en el paquete serviceimpl.
 *
 * MÉTODOS AGRUPADOS POR FUNCIÓN:
 *   - CRUD básico       → listarUsuarios, usuarioPorId, crearUsuario, borrarUsuario
 *   - Vínculo persona   → vincularPersona, desvincularPersona
 *   - Creación completa → crearUsuarioConPersonaYRol (web + API)
 *   - API REST          → listarUsuariosApi
 */
public interface UsuarioDetailsService {

    /** Retorna todos los usuarios (entidades, para vistas web). */
    List<Usuario> listarUsuarios();

    /** Busca un usuario por ID. Optional porque puede no existir. */
    Optional<Usuario> usuarioPorId(Integer id);

    /** Guarda un usuario en la BD (sin validaciones especiales, usado internamente). */
    Usuario crearUsuario(Usuario usuario);

    /** Elimina un usuario por su ID. */
    void borrarUsuario(Integer id);

    /**
     * Asocia una Persona existente a una cuenta de Usuario.
     * Actualiza el campo persona_id en la tabla usuarios.
     */
    void vincularPersona(Integer usuarioId, Integer personaId);

    /**
     * Elimina el vínculo entre un usuario y su persona.
     * Deja persona_id = NULL en la BD.
     */
    void desvincularPersona(Integer usuarioId);

    /**
     * CREACIÓN COMPLETA DE USUARIO (WEB + API)
     * ─────────────────────────────────────────
     * Crea un usuario nuevo con:
     *   1. Contraseña encriptada con BCrypt
     *   2. Persona vinculada (si se provee personaId)
     *   3. Rol asignado inmediatamente
     *
     * RESTRICCIÓN DE SEGURIDAD:
     *   Solo el usuario con ROL_SUPERADMIN puede asignar ROL_ADMIN.
     *   Esta regla se verifica usando el objeto Authentication del usuario autenticado.
     *
     * @param dto  datos del nuevo usuario (username, password, personaId opcional, rolNombre)
     * @param auth objeto de Spring Security con el usuario que hace la petición
     * @return UsuarioResponseDto con los datos del usuario creado (sin contraseña)
     * @throws IllegalArgumentException si el username ya existe, la persona ya tiene usuario,
     *                                  o el rol no existe
     * @throws SecurityException        si un no-superAdmin intenta asignar ROL_ADMIN
     */
    UsuarioResponseDto crearUsuarioConPersonaYRol(UsuarioCreateRequestDto dto, Authentication auth);

    /**
     * Lista todos los usuarios formateados como DTOs para la API REST.
     * No incluye contraseñas.
     */
    List<UsuarioResponseDto> listarUsuariosApi();
}
