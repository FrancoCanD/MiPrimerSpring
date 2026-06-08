package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.*;
import cl.kibernum.miprimerspringboot.service.AsistenciaService;
import cl.kibernum.miprimerspringboot.service.ClaseService;
import cl.kibernum.miprimerspringboot.repository.EstudianteRepository;
import cl.kibernum.miprimerspringboot.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * CONTROLADOR WEB DE ASISTENCIAS
 * ───────────────────────────────
 * Maneja el registro de asistencias desde el formulario web.
 * Acceso: ROL_ADMIN y ROL_INSTRUCTOR (configurado en SecurityConfig).
 *
 * DIFERENCIA CON OTROS CONTROLADORES:
 * En vez de @ModelAttribute con una entidad compleja, este controlador
 * recibe los datos del formulario como @RequestParam individuales.
 * Esto es porque Asistencia tiene relaciones (@ManyToOne) que Thymeleaf
 * no puede mapear automáticamente desde campos select/option del formulario.
 * Entonces se reciben los IDs y se cargan las entidades manualmente.
 */
@Controller
@RequestMapping("/asistencias")
public class AsistenciaController {

    @Autowired private AsistenciaService asistenciaService;
    @Autowired private EstudianteRepository estudianteRepository;
    @Autowired private InstructorRepository instructorRepository;
    @Autowired private ClaseService claseService;

    /**
     * GET /asistencias — lista todas las asistencias con sus datos relacionados.
     * La variable en el modelo se llama "listaAsistencias" (accesible como ${listaAsistencias} en HTML).
     */
    @GetMapping({"", "/listar"})
    public String listarAsistencias(Model model) {
        model.addAttribute("listaAsistencias", asistenciaService.listarTodas());
        return "asistencias/listar";
    }

    /**
     * GET /asistencias/nuevo — muestra el formulario con los dropdowns cargados.
     * Para que el usuario pueda seleccionar: estudiante, instructor y clase,
     * se envían las tres listas al modelo para poblar los <select> del HTML.
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("asistencia", new Asistencia());
        model.addAttribute("listaEstudiantes", estudianteRepository.findAll());
        model.addAttribute("listaInstructores", instructorRepository.findAll());
        model.addAttribute("listaClases", claseService.listarClases());
        return "asistencias/form";
    }

    /**
     * POST /asistencias/guardar — procesa el formulario de asistencia.
     *
     * Se reciben los parámetros del formulario por separado:
     *   - id (opcional, solo si es edición)
     *   - estudianteId, instructorId, claseId → IDs de los selects
     *   - fechaClase → String "2026-05-05" que se parsea a LocalDate
     *   - registro   → String "2026-05-05T20:30" que se parsea a LocalDateTime
     *
     * RedirectAttributes.addFlashAttribute() envía mensajes que se muestran
     * UNA SOLA VEZ después del redirect (mensajes flash).
     */
    @PostMapping("/guardar")
    public String guardarAsistencia(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "estudianteId") Integer estudianteId,
            @RequestParam(value = "instructorId") Integer instructorId,
            @RequestParam(value = "claseId") Integer claseId,
            @RequestParam(value = "fechaClase") String fechaClase,
            @RequestParam(value = "registro") String registro,
            RedirectAttributes redirectAttributes) {

        try {
            Asistencia asistencia = new Asistencia();
            // Si id no es null, JPA hará UPDATE; si es null, hará INSERT
            asistencia.setId(id);

            // Cargamos las entidades completas desde la BD usando los IDs recibidos
            Estudiante estudiante = estudianteRepository.findById(estudianteId)
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
            Instructor instructor = instructorRepository.findById(instructorId)
                    .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));
            Clase clase = claseService.listarClases().stream()
                    .filter(c -> c.getId().equals(claseId)).findFirst()
                    .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

            asistencia.setEstudiante(estudiante);
            asistencia.setInstructor(instructor);
            asistencia.setClase(clase);
            // LocalDate.parse convierte "2026-05-05" en un objeto LocalDate
            asistencia.setFechaClase(LocalDate.parse(fechaClase));
            // LocalDateTime.parse convierte "2026-05-05T20:30:00" en LocalDateTime
            asistencia.setRegistro(LocalDateTime.parse(registro));

            asistenciaService.guardar(asistencia);
            redirectAttributes.addFlashAttribute("exito", "Asistencia procesada correctamente");
            return "redirect:/asistencias/listar";

        } catch (Exception e) {
            // Si algo falla (ID inválido, formato de fecha incorrecto, etc.), mostramos el error
            redirectAttributes.addFlashAttribute("error", "Error en los datos: " + e.getMessage());
            return "redirect:/asistencias/nuevo";
        }
    }

    /**
     * GET /asistencias/editar/{id} — carga la asistencia existente y los dropdowns para editar.
     * Se pre-cargan las mismas listas que en /nuevo para que los selects funcionen.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Asistencia asistencia = asistenciaService.obtenerPorId(id);
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("listaEstudiantes", estudianteRepository.findAll());
        model.addAttribute("listaInstructores", instructorRepository.findAll());
        model.addAttribute("listaClases", claseService.listarClases());
        return "asistencias/form";
    }

    /** GET /asistencias/eliminar/{id} — elimina y redirige con mensaje de éxito. */
    @GetMapping("/eliminar/{id}")
    public String eliminarAsistencia(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        asistenciaService.eliminar(id);
        redirectAttributes.addFlashAttribute("exito", "Registro eliminado");
        return "redirect:/asistencias/listar";
    }
}
