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
 * Controlador MVC para manejar solicitudes web de grado
 */
@Controller
@RequestMapping("/grados")
public class GradoController {
    @Autowired
    private GradoService gradoService;
    /**
     * Muestra el listado de grados
     */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("grados", gradoService.listarGrados());
        return "grados/listar";
    }

    /**
     * Muestra el formulario para crear un nuevo grado
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("grado", new Grado());
        return "grados/form";
    }

    /**
     * Guarda un grado nuevo o actualizado
     */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("grado") Grado grado, BindingResult result) {
        if (result.hasErrors()) {
            return "grados/form";
        }
        gradoService.crearGrado(grado);
        return "redirect:/grados";
    }

    /**
     * Muestra el formulario con los datos cargados para la edición
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Grado grado = gradoService.gradoPorId(id).orElseThrow(() -> new IllegalArgumentException("Grado no encontrado" + id));
        model.addAttribute("grado", grado);
        return "grados/form";
    }

    /**
     * Elimina un Grado por el ID
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        gradoService.borrarGrado(id);
        return "redirect:/grados";
    }



}
