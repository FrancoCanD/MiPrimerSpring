package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.dto.FichaEstudianteDto;
import cl.kibernum.miprimerspringboot.service.EstudianteService;
import cl.kibernum.miprimerspringboot.service.GradoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * CONTROLADOR WEB DE ESTUDIANTES
 * ───────────────────────────────
 * Maneja el CRUD de alumnos y además dos vistas especiales:
 *   - /estudiantes/asistencias?rut=XXX → historial de asistencias del alumno
 *   - /estudiantes/perfil?rut=XXX      → ficha completa del alumno
 *
 * Acceso: ROL_ADMIN, ROL_INSTRUCTOR y ROL_ESTUDIANTE (ver SecurityConfig).
 */
@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private GradoService gradoService;

    /** GET /estudiantes — lista todos los alumnos. */
    @GetMapping({"", "/", "/listar"})
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.listarEstudiantes());
        return "estudiantes/listar";
    }

    /** GET /estudiantes/nuevo — formulario vacío + lista de grados para el dropdown. */
    @Secured({"ROL_ADMIN", "ROL_SUPERADMIN", "ROL_INSTRUCTOR"})
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("grados", gradoService.listarGrados());
        return "estudiantes/form";
    }

    /**
     * POST /estudiantes/guardar — guarda el alumno nuevo o actualizado.
     * Si hay errores → recarga el formulario CON la lista de grados para el dropdown.
     * (Si no se recarga la lista, el dropdown queda vacío y el formulario no funciona.)
     */
    @Secured({"ROL_ADMIN", "ROL_SUPERADMIN", "ROL_INSTRUCTOR"})
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("estudiante") Estudiante estudiante,
                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("grados", gradoService.listarGrados());
            return "estudiantes/form";
        }
        estudianteService.crearEstudiante(estudiante);
        return "redirect:/estudiantes";
    }

    /** GET /estudiantes/editar/{id} — carga los datos del alumno para editar. */
    @Secured({"ROL_ADMIN", "ROL_SUPERADMIN", "ROL_INSTRUCTOR"})
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Estudiante estudiante = estudianteService.estudiantePorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado: " + id));
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("grados", gradoService.listarGrados());
        return "estudiantes/form";
    }

    /** GET /estudiantes/eliminar/{id} — elimina el alumno y redirige. */
    @Secured({"ROL_ADMIN", "ROL_SUPERADMIN"})
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        estudianteService.borrarEstudiante(id);
        return "redirect:/estudiantes";
    }

    /**
     * VISTA ESPECIAL 1: HISTORIAL DE ASISTENCIAS DEL ALUMNO
     * ────────────────────────────────────────────────────────
     * GET /estudiantes/asistencias?rut=11111111-1
     *
     * @RequestParam("rut") extrae el parámetro de la URL (la parte después de ?rut=).
     * RedirectAttributes permite enviar mensajes flash a la vista de redirección
     *   (los mensajes flash se muestran una vez y luego desaparecen).
     *
     * try/catch: si el RUT no existe, se redirige al listado con un mensaje de error.
     */
    @GetMapping("/asistencias")
    public String verAsistencias(@RequestParam("rut") String rut,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            List<Asistencia> asistencias = estudianteService.listarAsistenciasPorRut(rut);
            Estudiante estudiante = estudianteService.buscarPorRut(rut)
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
            model.addAttribute("asistencias", asistencias);
            model.addAttribute("estudiante", estudiante);
            model.addAttribute("rut", rut);
            return "estudiantes/asistencias-estudiante";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "No se encontró un estudiante con RUT: " + rut);
            return "redirect:/estudiantes/listar";
        }
    }

    /**
     * VISTA ESPECIAL 2: PERFIL COMPLETO DEL ALUMNO
     * ──────────────────────────────────────────────
     * GET /estudiantes/perfil?rut=11111111-1
     *
     * Llama a obtenerFicha() que construye el FichaEstudianteDto con datos
     * personales, edad calculada, grado y todas sus asistencias.
     */
    @GetMapping("/perfil")
    public String verPerfil(@RequestParam("rut") String rut,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        try {
            FichaEstudianteDto ficha = estudianteService.obtenerFicha(rut);
            model.addAttribute("ficha", ficha);
            model.addAttribute("rut", rut);
            return "estudiantes/perfil-estudiante";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "No se encontró un estudiante con RUT: " + rut);
            return "redirect:/estudiantes/listar";
        }
    }
}
