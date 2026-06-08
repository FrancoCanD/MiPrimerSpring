package cl.kibernum.miprimerspringboot.restcontroller;

import cl.kibernum.miprimerspringboot.dto.request.UsuarioCreateRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.UsuarioResponseDto;
import cl.kibernum.miprimerspringboot.service.UsuarioDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CONTROLADOR REST DE USUARIOS
 * ─────────────────────────────
 * Expone la API REST para gestión de usuarios del sistema.
 * Acceso: ROL_ADMIN y ROL_SUPERADMIN (configurado en SecurityConfig).
 *
 * OPERACIONES DISPONIBLES:
 *   GET  /api/usuarios       → lista todos los usuarios (sin contraseñas)
 *   POST /api/usuarios       → crea un usuario con persona y rol asignados
 *
 * RESTRICCIÓN EN POST:
 *   Si el JSON incluye rolNombre = "ROL_ADMIN", el usuario autenticado
 *   debe tener ROL_SUPERADMIN. De lo contrario el servicio lanza SecurityException
 *   y Spring retorna HTTP 500. En producción se manejaría con @ExceptionHandler.
 *
 * AUTENTICACIÓN:
 *   Esta API usa HTTP Basic Auth (usuario:contraseña en Base64 en el header Authorization).
 *   Ejemplo con curl:
 *     curl -u superAdmin:1234 -X POST http://localhost:8080/api/usuarios \
 *          -H "Content-Type: application/json" \
 *          -d '{"username":"nuevoUser","password":"clave","personaId":4,"rolNombre":"ROL_INSTRUCTOR"}'
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioDetailsService usuarioService;

    /**
     * GET /api/usuarios
     * ─────────────────
     * Retorna la lista completa de usuarios como JSON.
     * Las contraseñas NUNCA se incluyen en la respuesta (UsuarioResponseDto no tiene ese campo).
     */
    @GetMapping
    @Operation(summary = "Listar todos los usuarios del sistema")
    public List<UsuarioResponseDto> listar() {
        return usuarioService.listarUsuariosApi();
    }

    /**
     * POST /api/usuarios
     * ───────────────────
     * Crea un nuevo usuario con persona vinculada y rol asignado.
     *
     * @Valid activa las validaciones del DTO (@NotBlank, @Size, etc.) antes de procesar.
     *   Si la validación falla → Spring retorna HTTP 400 con detalles del error.
     *
     * Authentication auth: Spring Security lo inyecta automáticamente con el usuario
     *   que hace la petición (autenticado vía HTTP Basic en la cadena apiSecurityFilterChain).
     *   Se pasa al servicio para verificar si puede asignar ROL_ADMIN.
     *
     * @ResponseStatus(CREATED) → retorna HTTP 201 en lugar del 200 por defecto.
     *   Es la respuesta semánticamente correcta cuando se crea un recurso nuevo.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear usuario con persona y rol (ROL_ADMIN solo para SuperAdmin)")
    public UsuarioResponseDto crear(
            @Valid @RequestBody UsuarioCreateRequestDto dto,
            Authentication auth) {
        return usuarioService.crearUsuarioConPersonaYRol(dto, auth);
    }
}
