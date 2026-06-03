package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.dto.FichaEstudianteDto;
import cl.kibernum.miprimerspringboot.service.EstudianteService;
import cl.kibernum.miprimerspringboot.service.GradoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controlador MVC para manejar solicitudes web de estudiante
 */
@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private GradoService gradoService;

    /**
     * Muestra el listado de Estudiantes
     */
    @GetMapping({"", "/", "/listar"})
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.listarEstudiantes());
        return "estudiantes/listar";
    }

    /**
     * Muestra el formulario para crear un nuevo estudiante
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("grados", gradoService.listarGrados());
        return "estudiantes/form";
    }

    /**
     * Guarda un estudiante nuevo o actualizado
     */
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

    /**
     * Muestra el formulario con los datos cargados para la edición
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Estudiante estudiante = estudianteService.estudiantePorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado: " + id));
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("grados", gradoService.listarGrados());
        return "estudiantes/form";
    }

    /**
     * Elimina un Estudiante por el ID
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        estudianteService.borrarEstudiante(id);
        return "redirect:/estudiantes";
    }

    // =========================================================
    // NUEVAS VISTAS: Asistencias por estudiante y Perfil
    // El RUT viene directamente desde los botones del listar.html
    // =========================================================

    /**
     * Vista 1 — Asistencias del estudiante
     * Se accede desde el botón "Asistencias" en listar.html
     * Muestra: fecha de clase, instructor, módulo/clase
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
     * Vista 2 — Perfil completo del estudiante
     * Se accede desde el botón "Perfil" en listar.html
     * Muestra: nombre, edad, grado, clases asistidas, instructores, fecha inscripción, vigente
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
