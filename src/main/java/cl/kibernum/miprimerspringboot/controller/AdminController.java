package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Persona;
import cl.kibernum.miprimerspringboot.bl.entity.Usuario;
import cl.kibernum.miprimerspringboot.repository.PersonaRepository;
import cl.kibernum.miprimerspringboot.repository.UsuarioRepository;
import cl.kibernum.miprimerspringboot.service.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * CONTROLADOR DE ADMINISTRACIÓN DE USUARIOS
 * ───────────────────────────────────────────
 * Maneja el panel para que el administrador pueda vincular y desvincular
 * personas (Estudiante/Instructor) a sus cuentas de usuario del sistema.
 *
 * @RequestMapping("/admin") establece un prefijo común para todas las rutas
 * de este controlador. Cada método agrega su propia sub-ruta.
 * Ejemplo: @GetMapping("/usuarios") → ruta completa: GET /admin/usuarios
 *
 * Acceso restringido a ROL_ADMIN (configurado en SecurityConfig).
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * Inyectamos el servicio de negocio de usuarios (interfaz UsuarioDetailsService).
     * Spring busca automáticamente la clase que implementa esa interfaz: UsuarioServiceImpl.
     */
    @Autowired private UsuarioDetailsService usuarioService;

    /**
     * Repositorio de personas: necesario para obtener la lista de personas disponibles
     * para vincular (las que aún no tienen un usuario asignado).
     */
    @Autowired private PersonaRepository personaRepository;

    /**
     * Repositorio de usuarios: usado para verificar si una persona ya está vinculada
     * a algún usuario (método existsByPersonaId).
     */
    @Autowired private UsuarioRepository usuarioRepository;

    /**
     * RUTA: GET /admin/usuarios
     * ──────────────────────────
     * Muestra la tabla con todos los usuarios y sus personas vinculadas.
     * Para los usuarios sin persona, muestra un dropdown con las personas disponibles.
     *
     * Lógica de "personas disponibles":
     *   1. Obtenemos TODAS las personas de la BD
     *   2. Filtramos solo aquellas que NO están asignadas a ningún usuario
     *      (usando existsByPersonaId: si retorna false, está disponible)
     *   3. Enviamos esa lista a la vista para poblar el dropdown
     */
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        // Cargamos todos los usuarios del sistema
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        // Obtenemos todas las personas y filtramos las que no tienen usuario vinculado.
        // stream() convierte la lista en un flujo de datos para procesarlo.
        // filter() descarta las personas que ya tienen usuario (existsByPersonaId = true).
        // toList() convierte el resultado de vuelta a una List.
        List<Persona> personasDisponibles = personaRepository.findAll().stream()
                .filter(p -> !usuarioRepository.existsByPersonaId(p.getId()))
                .toList();

        // Enviamos ambas listas a la vista Thymeleaf
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("personasDisponibles", personasDisponibles);

        return "admin/usuarios"; // Thymeleaf busca templates/admin/usuarios.html
    }

    /**
     * RUTA: POST /admin/usuarios/{id}/vincular-persona
     * ──────────────────────────────────────────────────
     * Recibe el formulario de la vista y vincula la persona seleccionada al usuario.
     *
     * @PathVariable Integer id: extrae el ID del usuario directamente de la URL.
     *   Ejemplo: POST /admin/usuarios/2/vincular-persona → id = 2
     *
     * @RequestParam Integer personaId: extrae el valor del campo "personaId"
     *   del formulario HTML (el select/dropdown).
     *
     * Usamos POST (no GET) porque está modificando datos en la BD.
     * Al finalizar, redirige de vuelta a la lista para ver el cambio aplicado.
     */
    @PostMapping("/usuarios/{id}/vincular-persona")
    public String vincularPersona(@PathVariable Integer id, @RequestParam Integer personaId) {
        usuarioService.vincularPersona(id, personaId);
        // "redirect:" le dice a Spring que envíe al navegador a esa URL (HTTP 302)
        // en vez de renderizar una vista directamente
        return "redirect:/admin/usuarios";
    }

    /**
     * RUTA: GET /admin/usuarios/{id}/desvincular-persona
     * ────────────────────────────────────────────────────
     * Elimina el vínculo entre el usuario indicado y su persona.
     * Deja persona_id = NULL en la base de datos.
     *
     * Usamos GET para simplificar el enlace en la vista (sin formulario),
     * igual que el patrón de los botones "Eliminar" del resto del proyecto.
     */
    @GetMapping("/usuarios/{id}/desvincular-persona")
    public String desvincularPersona(@PathVariable Integer id) {
        usuarioService.desvincularPersona(id);
        return "redirect:/admin/usuarios";
    }
}
