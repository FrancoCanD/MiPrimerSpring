package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO DE SALIDA PARA USUARIOS
 * ────────────────────────────
 * Datos que la API retorna al consultar o crear un usuario.
 * No incluye la contraseña (nunca debe exponerse, ni siquiera el hash).
 *
 * Ejemplo de JSON retornado:
 * {
 *   "id": 5,
 *   "username": "instructor2",
 *   "estado": true,
 *   "personaId": 4,
 *   "personaNombreCompleto": "Andrea González",
 *   "roles": ["ROL_INSTRUCTOR"]
 * }
 *
 * Si el usuario no tiene persona vinculada (cuenta de sistema):
 * {
 *   "id": 1,
 *   "username": "admin",
 *   "estado": true,
 *   "personaId": null,
 *   "personaNombreCompleto": null,
 *   "roles": ["ROL_ADMIN"]
 * }
 */
@Getter
@Setter
public class UsuarioResponseDto {

    private Integer id;

    private String username;

    /** true = cuenta activa, false = cuenta deshabilitada. */
    private Boolean estado;

    /** ID de la persona vinculada, o null si es cuenta de sistema. */
    private Integer personaId;

    /**
     * Nombre completo de la persona vinculada (nombres + apellido1).
     * Null si el usuario no tiene persona vinculada.
     */
    private String personaNombreCompleto;

    /**
     * Lista de nombres de roles asignados.
     * Ejemplo: ["ROL_ADMIN"] o ["ROL_ADMIN", "ROL_SUPERADMIN"]
     */
    private List<String> roles;
}
