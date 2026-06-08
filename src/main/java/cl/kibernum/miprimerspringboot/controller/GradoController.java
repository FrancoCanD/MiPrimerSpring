package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import cl.kibernum.miprimerspringboot.service.GradoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * CONTROLADOR WEB DE GRADOS
 * ──────────────────────────
 * Maneja las peticiones HTTP del navegador para el CRUD de grados/cinturones.
 * Devuelve nombres de plantillas Thymeleaf (vistas HTML), no JSON.
 *
 * @RequestMapping("/grados") → todas las rutas de este controlador empiezan con /grados.
 * Acceso restringido a ROL_ADMIN (configurado en SecurityConfig).
 */
@Controller
@RequestMapping("/grados")
public class GradoController {

    @Autowired
    private GradoService gradoService;

    /**
     * GET /grados  o  GET /grados/listar
     * Lista todos los grados y los envía a la vista.
     * model.addAttribute("grados", ...) hace que ${grados} esté disponible en el HTML.
     */
    @GetMapping({"", "/", "/listar"})
    public String listar(Model model) {
        model.addAttribute("grados", gradoService.listarGrados());
        return "grados/listar"; // → templates/grados/listar.html
    }

    /**
     * GET /grados/nuevo
     * Muestra el formulario vacío para crear un grado.
     * Se envía un objeto Grado vacío al modelo para que Thymeleaf lo vincule
     * con los campos del formulario (th:object="${grado}").
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("grado", new Grado());
        return "grados/form"; // → templates/grados/form.html
    }

    /**
     * POST /grados/guardar
     * Recibe y procesa el formulario de creación/edición de un grado.
     *
     * @Valid         → activa las validaciones de Bean Validation (@NotBlank, etc.) en el objeto
     * @ModelAttribute → Spring toma los valores del formulario y los mapea al objeto Grado
     * BindingResult → contiene los errores de validación si @Valid falla
     *
     * Si hay errores → se vuelve a mostrar el formulario con los mensajes de error.
     * Si no hay errores → guarda y redirige al listado (redirect evita reenviar el formulario).
     */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("grado") Grado grado, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "grados/form";
        }
        gradoService.crearGrado(grado);
        return "redirect:/grados";
    }

    /**
     * GET /grados/editar/{id}
     * Carga los datos del grado en el formulario para poder editarlos.
     * @PathVariable extrae el {id} de la URL.
     * orElseThrow lanza excepción si el ID no existe, evitando NullPointerException.
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Grado grado = gradoService.gradoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Grado no encontrado: " + id));
        model.addAttribute("grado", grado);
        return "grados/form";
    }

    /**
     * GET /grados/eliminar/{id}
     * Elimina el grado y redirige al listado.
     * Nota: si el grado está asignado a un estudiante/instructor, la BD lanzará
     * un error de FK que el usuario verá como mensaje de alerta en la vista.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        gradoService.borrarGrado(id);
        return "redirect:/grados";
    }
}
