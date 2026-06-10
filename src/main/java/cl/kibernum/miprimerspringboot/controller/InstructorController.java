package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Instructor;
import cl.kibernum.miprimerspringboot.service.InstructorService;
import cl.kibernum.miprimerspringboot.service.GradoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * CONTROLADOR WEB DE INSTRUCTORES
 * ────────────────────────────────
 * Maneja el CRUD de instructores en las vistas Thymeleaf.
 * Acceso: ROL_ADMIN y ROL_INSTRUCTOR (configurado en SecurityConfig).
 *
 * Necesita dos servicios:
 *   - InstructorService: operaciones CRUD sobre instructores
 *   - GradoService: para cargar la lista de grados en el formulario (dropdown de cinturones)
 */
@Controller
@RequestMapping("/instructores")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private GradoService gradoService;

    /** GET /instructores — lista todos los instructores. */
    @GetMapping({"", "/", "/listar"})
    public String listar(Model model) {
        model.addAttribute("instructores", instructorService.listarInstructores());
        return "instructores/listar";
    }

    /**
     * GET /instructores/nuevo — formulario vacío.
     * Se agrega también la lista de grados al modelo para poblar el dropdown de cinturones.
     */
    @Secured({"ROL_ADMIN", "ROL_SUPERADMIN"})
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("instructor", new Instructor());
        model.addAttribute("grados", gradoService.listarGrados());
        return "instructores/form";
    }

    /**
     * POST /instructores/guardar — procesa el formulario.
     * Si hay errores → recarga el formulario con la lista de grados (necesaria para el dropdown).
     */
    @Secured({"ROL_ADMIN", "ROL_SUPERADMIN"})
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("instructor") Instructor instructor,
                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("grados", gradoService.listarGrados());
            return "instructores/form";
        }
        instructorService.crearInstructor(instructor);
        return "redirect:/instructores";
    }

    /** GET /instructores/editar/{id} — carga los datos del instructor más la lista de grados. */
    @Secured({"ROL_ADMIN", "ROL_SUPERADMIN"})
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Instructor instructor = instructorService.instructorPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Instructor no encontrado: " + id));
        model.addAttribute("instructor", instructor);
        model.addAttribute("grados", gradoService.listarGrados());
        return "instructores/form";
    }

    /** GET /instructores/eliminar/{id} — elimina y redirige al listado. */
    @Secured({"ROL_ADMIN", "ROL_SUPERADMIN"})
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        instructorService.borrarInstructor(id);
        return "redirect:/instructores";
    }
}
