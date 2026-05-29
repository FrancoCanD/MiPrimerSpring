package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Clase;
import cl.kibernum.miprimerspringboot.service.ClaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador MVC para manejar solicitudes web de clase
 */
@Controller
@RequestMapping("/clases")
public class ClaseController {
    @Autowired
    private ClaseService claseService;

    /**
     * Muestra el listado de clases
     */
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("clases", claseService.listarClases());
        return "clases/listar";
    }

    /**
     * Muestra el formulario para crear una nueva clase
     */
    @GetMapping("/nuevo")
        public String nuevo(Model model) {
        model.addAttribute("clase", new Clase());
        return "clases/form";
    }

    /**
     * Guarda una clase nueva o actualizada
     */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("clase") Clase clase, BindingResult result, Model model) { // Corregido: Agregado Model model
        if (result.hasErrors()) {
            return "clases/form"; // Ahora recargará la vista sin errores de renderizado
        }
        claseService.crearClase(clase);
        return "redirect:/clases";
    }

    /**
     * Muestra el formulario de clases con los datos cargados para la edición
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Clase clase = claseService.clasePorId(id).orElseThrow(() -> new IllegalArgumentException("Clase no encontrada" + id));
        model.addAttribute("clase", clase);
        return "clases/form";
    }

    /**
     * Elimina una clase por ID
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        claseService.borrarClase(id);
        return "redirect:/clases";
    }
}
