package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.service.EstudianteService;
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
    private  EstudianteService estudianteService;
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
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "estudiantes/form";
    }

    /**
     * Guarda un estudiante nuevo o actualizado
     */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("estudiante") Estudiante estudiante, BindingResult result) {
        if (result.hasErrors()) {
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
        Estudiante estudiante = estudianteService.estudiantePorId(id).orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado" + id));
        model.addAttribute("estudiante", estudiante);
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
