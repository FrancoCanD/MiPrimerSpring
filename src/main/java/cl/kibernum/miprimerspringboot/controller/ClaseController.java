package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Clase;
import cl.kibernum.miprimerspringboot.service.ClaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * CONTROLADOR WEB DE CLASES
 * ──────────────────────────
 * Maneja el CRUD de tipos de clase en las vistas Thymeleaf.
 * Acceso: ROL_ADMIN y ROL_INSTRUCTOR (configurado en SecurityConfig).
 *
 * Mismo patrón que GradoController: listar, nuevo, guardar, editar, eliminar.
 */
@Controller
@RequestMapping("/clases")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    /** GET /clases — muestra listado de todas las clases. */
    @GetMapping({"", "/", "/listar"})
    public String listar(Model model) {
        model.addAttribute("clases", claseService.listarClases());
        return "clases/listar";
    }

    /** GET /clases/nuevo — formulario vacío para crear una clase. */
    @Secured({"ROL_ADMIN", "ROL_SUPERADMIN"})
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("clase", new Clase());
        return "clases/form";
    }

    /**
     * POST /clases/guardar — procesa el formulario.
     * Si hay errores de validación → vuelve al formulario.
     * Si todo es correcto → guarda y redirige al listado.
     */
    @Secured({"ROL_ADMIN", "ROL_SUPERADMIN"})
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("clase") Clase clase, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "clases/form";
        }
        claseService.crearClase(clase);
        return "redirect:/clases";
    }

    /** GET /clases/editar/{id} — carga los datos de la clase para editar. */
    @Secured({"ROL_ADMIN", "ROL_SUPERADMIN"})
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Clase clase = claseService.clasePorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada: " + id));
        model.addAttribute("clase", clase);
        return "clases/form";
    }

    /** GET /clases/eliminar/{id} — elimina la clase y redirige. */
    @Secured({"ROL_ADMIN", "ROL_SUPERADMIN"})
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        claseService.borrarClase(id);
        return "redirect:/clases";
    }
}
