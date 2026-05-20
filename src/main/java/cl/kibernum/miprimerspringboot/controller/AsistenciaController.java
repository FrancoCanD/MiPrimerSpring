package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.service.AsistenciaService;
import cl.kibernum.miprimerspringboot.service.ClaseService;
import cl.kibernum.miprimerspringboot.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ClaseService claseService;

    // 1. LISTAR ASISTENCIAS
    @GetMapping({"", "/listar"})
    public String listarAsistencias(Model model) {
        model.addAttribute("listaAsistencias", asistenciaService.listarTodas());
        return "asistencias/listar"; // Busca templates/asistencias/listar.html
    }

    // 2. MOSTRAR FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("asistencia", new Asistencia());
        model.addAttribute("listaPersonas", estudianteRepository.findAll());
        model.addAttribute("listaClases", claseService.listarClases());
        return "asistencias/form";
    }

    // 3. PROCESAR GUARDADO / EDICIÓN
    @PostMapping("/guardar")
    public String guardarAsistencia(
            @ModelAttribute("asistencia") Asistencia asistencia,
            @RequestParam(value = "persona.id", required = false) Integer personaId,
            @RequestParam(value = "clase.id", required = false) Integer claseId,
            Model model) {

        try {
            //Buscamos manualmente la persona en el repositorio usando el ID del formulario
            if (personaId != null) {
                //Buscamos en el repositorio de estudiantes (que heredan de Persona)
                cl.kibernum.miprimerspringboot.bl.entity.Estudiante estudiante =
                        estudianteRepository.findById(personaId).orElse(null);
                asistencia.setPersona(estudiante);
            }

            //Buscamos manualmente la clase asignada en el servicio
            if (claseId != null) {
                cl.kibernum.miprimerspringboot.bl.entity.Clase clase =
                        claseService.clasePorId(claseId).orElse(null);
                asistencia.setClase(clase);
            }

            //Persistimos de forma limpia
            asistenciaService.guardar(asistencia);
            return "redirect:/asistencias/listar";

        } catch (Exception e) {
            // En caso de fallas de base de datos, recargamos el formulario de forma segura sin caídas
            model.addAttribute("listaPersonas", estudianteRepository.findAll());
            model.addAttribute("listaClases", claseService.listarClases());
            model.addAttribute("error", "Error al registrar la asistencia: " + e.getMessage());
            return "asistencias/form"; // Cambia por "asistencias/formulario" si usas el nombre largo
        }
    }




    // 4. MOSTRAR FORMULARIO PARA EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Asistencia asistencia = asistenciaService.obtenerPorId(id);
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("listaPersonas", estudianteRepository.findAll());
        model.addAttribute("listaClases", claseService.listarClases());
        return "asistencias/form";
    }

    // 5. ELIMINAR REGISTRO
    @GetMapping("/eliminar/{id}")
    public String eliminarAsistencia(@PathVariable("id") Integer id) {
        asistenciaService.eliminar(id);
        return "redirect:/asistencias/listar";
    }
}
