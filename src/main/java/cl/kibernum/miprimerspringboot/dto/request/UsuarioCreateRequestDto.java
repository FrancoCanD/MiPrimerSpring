package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO DE ENTRADA PARA CREAR UN USUARIO
 * ──────────────────────────────────────
 * Contiene los datos que debe enviar el cliente (formulario web o API REST)
 * para crear una nueva cuenta de usuario en el sistema.
 *
 * Campos requeridos: username, password, rolNombre.
 * Campo opcional   : personaId (un admin de sistema puede no tener persona vinculada).
 *
 * Ejemplo de JSON para la API:
 * {
 *   "username": "instructor2",
 *   "password": "clave123",
 *   "personaId": 4,
 *   "rolNombre": "ROL_INSTRUCTOR"
 * }
 *
 * RESTRICCIÓN DE ROL:
 *   Solo el usuario con ROL_SUPERADMIN puede enviar rolNombre = "ROL_ADMIN".
 *   Esta regla se verifica en UsuarioServiceImpl, no aquí.
 */
@Getter
@Setter
public class UsuarioCreateRequestDto {

    /**
     * Nombre de usuario único para iniciar sesión.
     * @NotBlank rechaza null, "" y strings solo de espacios.
     */
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    /**
     * Contraseña en texto plano.
     * El servicio la encripta con BCrypt antes de guardarla en la BD.
     * Nunca se almacena en texto plano.
     * @Size mínimo 4 caracteres como política básica.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;

    /**
     * ID de la persona (Estudiante o Instructor) a vincular.
     * Es OPCIONAL: puede ser null para cuentas administrativas sin persona física asociada.
     * El servicio verifica que la persona exista y que no tenga ya un usuario asignado.
     */
    private Integer personaId;

    /**
     * Nombre exacto del rol a asignar.
     * Debe coincidir con un nombre en la tabla "roles": "ROL_ADMIN", "ROL_INSTRUCTOR", "ROL_ESTUDIANTE".
     * "ROL_SUPERADMIN" nunca es asignable desde este DTO.
     */
    @NotBlank(message = "Debe seleccionar un rol")
    private String rolNombre;
}
