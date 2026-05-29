package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Instructor;
import cl.kibernum.miprimerspringboot.service.InstructorService;
import cl.kibernum.miprimerspringboot.service.GradoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador MVC para manejar solicitudes web de instructor
 */
@Controller
@RequestMapping("/instructores")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private GradoService gradoService;

    /**
     * Muestra el listado de Instructores
     */
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("instructores", instructorService.listarInstructores());
        return "instructores/listar";
    }

    /**
     * Muestra el formulario para crear un nuevo instructor
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("instructor", new Instructor());
        model.addAttribute("grados", gradoService.listarGrados());
        return "instructores/form";
    }

    /**
     * Guarda un instructor nuevo o actualizado
     */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("instructor") Instructor instructor,
                          BindingResult result, Model model) { // Asegurado el parámetro Model
        if (result.hasErrors()) {
            model.addAttribute("grados", gradoService.listarGrados());
            return "instructores/form"; // Recarga de forma segura con la lista de cinturones
        }
        instructorService.crearInstructor(instructor);
        return "redirect:/instructores";
    }

    /**
     * Muestra el formulario con los datos cargados para la edición
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Instructor instructor = instructorService.instructorPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Instructor no encontrado: " + id));
        model.addAttribute("instructor", instructor);
        model.addAttribute("grados", gradoService.listarGrados());
        return "instructores/form";
    }

    /**
     * Elimina un Instructor por el ID
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        instructorService.borrarInstructor(id);
        return "redirect:/instructores";
    }
}
