package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.service.AsistenciaService; // ← Asegúrate de importar el servicio
import cl.kibernum.miprimerspringboot.service.EstudianteService;
import cl.kibernum.miprimerspringboot.service.GradoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
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

    @Autowired
    private AsistenciaService asistenciaService; // ← Inyección única corregida

    /**
     * Muestra el listado de Estudiantes
     */
    @GetMapping({"", "/", "/listar"})
    public String listar(Model model) {
        model.addAttribute("students", estudianteService.listarEstudiantes()); // Ajustado a tu variable original si aplica
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

    /**
     * Muestra el perfil de un estudiante específico junto a sus asistencias
     */
    @GetMapping("/perfil/{id}")
    public String perfil(@PathVariable Integer id, Model model) {
        Estudiante estudiante = estudianteService.estudiantePorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado: " + id));

        int edad = 0;
        if (estudiante.getFechaNac() != null) {
            edad = Period.between(estudiante.getFechaNac(), LocalDate.now()).getYears();
        }

        List<Asistencia> asistencias = asistenciaService.listarPorEstudianteId(id);

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("edad", edad);
        model.addAttribute("grados", gradoService.listarGrados());
        model.addAttribute("asistencias", asistencias);

        return "estudiantes/perfil";
    }
}
