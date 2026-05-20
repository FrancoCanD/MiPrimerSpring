package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.service.EstudianteService;
import cl.kibernum.miprimerspringboot.service.GradoService; // 💡 Asegúrate de importar el servicio de grados
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador MVC para manejar solicitudes web de estudiante
 */
@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    // 💡 1. AQUÍ INYECTAS EL SERVICIO DE GRADOS (Abajo de estudianteService)
    @Autowired
    private GradoService gradoService;

    /**
     * Muestra el listado de Estudiantes
     */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.listarEstudiantes());
        return "estudiantes/listar";
    }

    /**
     * Muestra el formulario para crear un nuevo estudiante
     * 💡 2. AQUÍ COLOCAS EL NUEVO MÉTODO 'nuevo' REEMPLAZANDO EL ANTERIOR
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Estudiante estudiante = new Estudiante();
        // Inicializa el grado para evitar errores de objeto nulo en la vista
        estudiante.setGrado(new cl.kibernum.miprimerspringboot.bl.entity.Grado());
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("grados", gradoService.listarGrados()); // Envía los cinturones al SELECT del HTML
        return "estudiantes/form"; // Revisa si tu archivo se llama form.html o formulario.html
    }

    /**
     * Guarda un estudiante nuevo o actualizado
     */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("estudiante") Estudiante estudiante, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Si hay errores de validación, reinyectamos los grados para que no falle el SELECT
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
        model.addAttribute("grados", gradoService.listarGrados()); // Envía los cinturones al SELECT en la edición
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
}
