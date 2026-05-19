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
    @GetMapping
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
    public String guardar(@Valid @ModelAttribute("clase") Clase clase, BindingResult result) {
        if (result.hasErrors()) {
            return "clases/form";
        }
        claseService.crearClase(clase);
        return "redirect:/clases";
    }

    /**
     * Muestra el formulario de clases con los datos cargados para la edición
     */
    @GetMapping("/editar/{idClase}")
    public String editar(@PathVariable Integer idClase, Model model) {
        Clase clase = claseService.clasePorId(idClase).orElseThrow(() -> new IllegalArgumentException("Clase no encontrada" + idClase));
        model.addAttribute("clase", clase);
        return "clases/form";
    }

    /**
     * Elimina una clase por ID
     */
    @GetMapping("/eliminar/{idClase}")
    public String eliminar(@PathVariable Integer idClase) {
        claseService.borrarClase(idClase);
        return "redirect:/clases";
    }
}
