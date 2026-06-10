package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Usuario;
import cl.kibernum.miprimerspringboot.service.serviceimpl.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * CONTROLADOR DE AUTENTICACIÓN
 * ─────────────────────────────
 * Maneja las rutas relacionadas con la sesión del usuario:
 * inicio de sesión, panel de inicio y página de acceso denegado.
 *
 * @Controller indica que esta clase es un controlador MVC de Spring:
 * recibe peticiones HTTP y devuelve el nombre de una vista Thymeleaf.
 */
@Controller
public class AuthController {

    /**
     * @Autowired inyecta el repositorio automáticamente.
     * Lo necesitamos para cargar los datos completos del usuario autenticado
     * (incluyendo su persona vinculada) y enviárselos a la vista.
     */
    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    /**
     * RUTA: GET /login
     * ─────────────────
     * Muestra el formulario de inicio de sesión.
     * Spring Security intercepta el POST de ese formulario automáticamente,
     * por eso aquí solo manejamos el GET (mostrar la página).
     *
     * Retorna "auth/login" → Thymeleaf busca templates/auth/login.html
     */
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    /**
     * RUTA: GET /dashboard-instructor
     * ──────────────────────────────────
     * Dashboard personalizado para el instructor autenticado.
     */
    @GetMapping("/dashboard-instructor")
    public String dashboardInstructor(Authentication auth, Model model) {
        Usuario usuario = usuarioDetailsService.encontrarPorUsername(auth);
        model.addAttribute("usuario", usuario);
        return "dashboards/dashboard-instructor";
    }

    /**
     * RUTA: GET /dashboard-estudiante
     * ──────────────────────────────────
     * Dashboard personalizado para el estudiante autenticado.
     */
    @GetMapping("/dashboard-estudiante")
    public String dashboardEstudiante(Authentication auth, Model model) {
        Usuario usuario = usuarioDetailsService.encontrarPorUsername(auth);
        model.addAttribute("usuario", usuario);
        return "dashboards/dashboard-estudiante";
    }

    /**
     * RUTA: GET /inicio
     * ──────────────────
     * Panel principal del usuario autenticado con menú filtrado por rol.
     *
     * Authentication auth: Spring Security lo inyecta automáticamente.
     *   Contiene información del usuario que inició sesión (nombre, roles, etc.).
     *
     * Model model: objeto para enviar datos desde el controlador a la vista HTML.
     *   Es como un "maletín" donde metemos los datos que necesita la página.
     *
     * Flujo:
     *   1. auth.getName() obtiene el username del usuario autenticado
     *   2. Buscamos el objeto Usuario completo en la BD (con su persona vinculada)
     *   3. Lo enviamos a la vista con model.addAttribute()
     *   4. En inicio.html se accede con ${usuario.persona.nombres}, etc.
     */
    @GetMapping("/inicio")
    public String inicio(Authentication auth, Model model) {
        // Cargamos el usuario completo desde la BD usando el username de la sesión activa
        Usuario usuario = usuarioDetailsService.encontrarPorUsername(auth);

        // Agregamos el usuario al modelo con la clave "usuario"
        // En la vista se accede así: ${usuario.username}, ${usuario.persona.nombres}, etc.
        model.addAttribute("usuario", usuario);

        return "inicio"; // Thymeleaf busca templates/inicio.html
    }

    /**
     * RUTA: GET /acceso-denegado
     * ───────────────────────────
     * Se muestra cuando un usuario autenticado intenta acceder a una ruta
     * para la que no tiene los permisos necesarios (error HTTP 403).
     * Spring Security redirige aquí gracias a .accessDeniedPage("/acceso-denegado")
     * configurado en SecurityConfig.
     */
    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "auth/acceso-denegado"; // Thymeleaf busca templates/auth/acceso-denegado.html
    }
}
