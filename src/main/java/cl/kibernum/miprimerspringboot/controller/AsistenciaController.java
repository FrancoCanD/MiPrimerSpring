package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.bl.entity.Clase;
import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.service.AsistenciaService;
import cl.kibernum.miprimerspringboot.service.ClaseService;
import cl.kibernum.miprimerspringboot.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ClaseService claseService;

    // 1. LISTAR
    @GetMapping({"", "/listar"})
    public String listarAsistencias(Model model) {
        model.addAttribute("listaAsistencias", asistenciaService.listarTodas());
        return "asistencias/listar";
    }

    // 2. FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("asistencia", new Asistencia());
        model.addAttribute("listaPersonas", estudianteRepository.findAll());
        model.addAttribute("listaClases", claseService.listarClases());
        return "asistencias/form";
    }

    // 3. GUARDAR - CORREGIDO con validaciones y estudiante_id
    @PostMapping("/guardar")
    public String guardarAsistencia(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "personaId", required = false) Integer personaId,
            @RequestParam(value = "claseId", required = false) Integer claseId,
            @RequestParam(value = "fechaClase", required = false) String fechaClase,
            @RequestParam(value = "registro", required = false) String registro,
            RedirectAttributes redirectAttributes,
            Model model) {

        // VALIDACIONES
        if (personaId == null) {
            redirectAttributes.addFlashAttribute("error", "Debe seleccionar un estudiante");
            return "redirect:/asistencias/nuevo";
        }

        if (claseId == null) {
            redirectAttributes.addFlashAttribute("error", "Debe seleccionar una clase");
            return "redirect:/asistencias/nuevo";
        }

        if (fechaClase == null || fechaClase.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Debe ingresar la fecha de la clase");
            return "redirect:/asistencias/nuevo";
        }

        if (registro == null || registro.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Debe ingresar la fecha/hora de registro");
            return "redirect:/asistencias/nuevo";
        }

        try {
            Asistencia asistencia = new Asistencia();
            asistencia.setId(id);

            // Obtener el estudiante
            Estudiante estudiante = estudianteRepository.findById(personaId)
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

            // Establecer AMBAS relaciones (persona y estudiante)
            asistencia.setPersona(estudiante);  // Estudiante hereda de Persona
            asistencia.setEstudiante(estudiante); // Relación específica

            // Para Clase: usa claseService para obtener la referencia
            Clase clase = new Clase();
            clase.setId(claseId);
            asistencia.setClase(clase);

            asistencia.setFechaClase(LocalDate.parse(fechaClase));
            asistencia.setRegistro(LocalDateTime.parse(registro));

            asistenciaService.guardar(asistencia);

            redirectAttributes.addFlashAttribute("exito", "Asistencia guardada correctamente");
            return "redirect:/asistencias/listar";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/asistencias/nuevo";
        }
    }

    // 4. FORMULARIO EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Asistencia asistencia = asistenciaService.obtenerPorId(id);
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("listaPersonas", estudianteRepository.findAll());
        model.addAttribute("listaClases", claseService.listarClases());
        return "asistencias/form";
    }

    // 5. ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarAsistencia(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            asistenciaService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "Asistencia eliminada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/asistencias/listar";
    }
}