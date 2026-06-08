package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Persona;
import cl.kibernum.miprimerspringboot.bl.entity.Rol;
import cl.kibernum.miprimerspringboot.bl.entity.Usuario;
import cl.kibernum.miprimerspringboot.dto.request.UsuarioCreateRequestDto;
import cl.kibernum.miprimerspringboot.repository.PersonaRepository;
import cl.kibernum.miprimerspringboot.repository.RolRepository;
import cl.kibernum.miprimerspringboot.repository.UsuarioRepository;
import cl.kibernum.miprimerspringboot.service.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * CONTROLADOR DE ADMINISTRACIÓN DE USUARIOS
 * ───────────────────────────────────────────
 * Maneja el panel de administración de cuentas de usuario:
 *   - Listar todos los usuarios con sus personas y roles
 *   - Crear nuevos usuarios (con persona y rol asignados de inmediato)
 *   - Vincular / desvincular personas a cuentas existentes
 *
 * Acceso: ROL_ADMIN y ROL_SUPERADMIN (configurado en SecurityConfig).
 *
 * RESTRICCIÓN ESPECIAL:
 *   Al cargar la vista, el controlador filtra los roles disponibles según
 *   el rol del usuario autenticado:
 *     - ROL_ADMIN     → puede asignar ROL_INSTRUCTOR y ROL_ESTUDIANTE
 *     - ROL_SUPERADMIN→ puede asignar además ROL_ADMIN
 *   ROL_SUPERADMIN nunca aparece en el formulario (no es asignable desde la UI).
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UsuarioDetailsService usuarioService;
    @Autowired private PersonaRepository personaRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    /**
     * RolRepository: necesario para cargar la lista de roles disponibles
     * y filtrarlos según el privilegio del usuario autenticado.
     */
    @Autowired private RolRepository rolRepository;

    /**
     * GET /admin/usuarios
     * ────────────────────
     * Carga y envía a la vista:
     *   - Lista de todos los usuarios del sistema
     *   - Personas disponibles para vincular (sin usuario asignado)
     *   - Roles disponibles para crear nuevos usuarios (filtrados por privilegio)
     *   - Flag isSuperAdmin para que la vista muestre/oculte opciones especiales
     *
     * Authentication auth: Spring Security inyecta el objeto con el usuario autenticado.
     * auth.getAuthorities() retorna los roles del usuario actual como strings.
     */
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model, Authentication auth) {

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        // Personas sin usuario asignado (disponibles para vincular o asignar al crear)
        List<Persona> personasDisponibles = personaRepository.findAll().stream()
                .filter(p -> !usuarioRepository.existsByPersonaId(p.getId()))
                .toList();

        // Determinamos si el usuario actual es superAdmin
        boolean esSuperAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROL_SUPERADMIN"));

        // Filtramos los roles disponibles para crear usuarios:
        //   - ROL_SUPERADMIN nunca es asignable desde la UI (solo existe en data.sql)
        //   - ROL_ADMIN solo lo puede asignar ROL_SUPERADMIN
        List<Rol> rolesDisponibles = rolRepository.findAll().stream()
                .filter(r -> !r.getNombre().equals("ROL_SUPERADMIN"))
                .filter(r -> esSuperAdmin || !r.getNombre().equals("ROL_ADMIN"))
                .toList();

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("personasDisponibles", personasDisponibles);
        model.addAttribute("rolesDisponibles", rolesDisponibles);
        // La vista usa este flag para mostrar mensajes informativos contextuales
        model.addAttribute("esSuperAdmin", esSuperAdmin);

        return "admin/usuarios";
    }

    /**
     * POST /admin/usuarios/crear
     * ───────────────────────────
     * Procesa el formulario de creación de un nuevo usuario.
     * Recibe cada campo como @RequestParam individual para un manejo más seguro y explícito.
     *
     * @RequestParam(required = false) Integer personaId:
     *   required = false → si el campo viene vacío (""), Spring lo convierte a null
     *   automáticamente para tipos Integer (wrapper). Esto permite que el campo sea opcional.
     *
     * Delega la lógica completa (validación, encriptado, vínculo, rol) al servicio.
     * Los errores se capturan con try/catch y se muestran como mensajes flash en la vista.
     *
     * RedirectAttributes: permite enviar mensajes que se muestran UNA SOLA VEZ
     * después del redirect (desaparecen tras recargar la página).
     */
    @PostMapping("/usuarios/crear")
    public String crearUsuario(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) Integer personaId,
            @RequestParam String rolNombre,
            Authentication auth,
            RedirectAttributes redirectAttributes) {

        // Construimos el DTO con los parámetros recibidos del formulario
        UsuarioCreateRequestDto dto = new UsuarioCreateRequestDto();
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setPersonaId(personaId);
        dto.setRolNombre(rolNombre);

        try {
            usuarioService.crearUsuarioConPersonaYRol(dto, auth);
            redirectAttributes.addFlashAttribute("exito",
                    "Usuario '" + username + "' creado correctamente con rol " + rolNombre + ".");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "No se pudo crear el usuario: " + e.getMessage());
        }

        return "redirect:/admin/usuarios";
    }

    /**
     * POST /admin/usuarios/{id}/vincular-persona
     * ────────────────────────────────────────────
     * Vincula una persona existente a un usuario ya creado (sin rol).
     * Usado desde la tabla de usuarios para los que se crearon sin persona.
     */
    @PostMapping("/usuarios/{id}/vincular-persona")
    public String vincularPersona(@PathVariable Integer id, @RequestParam Integer personaId) {
        usuarioService.vincularPersona(id, personaId);
        return "redirect:/admin/usuarios";
    }

    /**
     * GET /admin/usuarios/{id}/desvincular-persona
     * ──────────────────────────────────────────────
     * Elimina el vínculo entre un usuario y su persona (persona_id = NULL en BD).
     */
    @GetMapping("/usuarios/{id}/desvincular-persona")
    public String desvincularPersona(@PathVariable Integer id) {
        usuarioService.desvincularPersona(id);
        return "redirect:/admin/usuarios";
    }
}
