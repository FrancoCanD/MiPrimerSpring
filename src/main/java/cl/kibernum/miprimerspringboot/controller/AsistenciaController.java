package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.*;
import cl.kibernum.miprimerspringboot.service.AsistenciaService;
import cl.kibernum.miprimerspringboot.service.ClaseService;
import cl.kibernum.miprimerspringboot.repository.EstudianteRepository;
import cl.kibernum.miprimerspringboot.repository.InstructorRepository; // Inyección requerida
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

    @Autowired private AsistenciaService asistenciaService;
    @Autowired private EstudianteRepository estudianteRepository;
    @Autowired private InstructorRepository instructorRepository; // Inyectado
    @Autowired private ClaseService claseService;

    @GetMapping({"", "/listar"})
    public String listarAsistencias(Model model) {
        model.addAttribute("listaAsistencias", asistenciaService.listarTodas());
        return "asistencias/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("asistencia", new Asistencia());
        model.addAttribute("listaEstudiantes", estudianteRepository.findAll());
        model.addAttribute("listaInstructores", instructorRepository.findAll()); // Enviados a la vista
        model.addAttribute("listaClases", claseService.listarClases());
        return "asistencias/form";
    }

    @PostMapping("/guardar")
    public String guardarAsistencia(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "estudianteId") Integer estudianteId,
            @RequestParam(value = "instructorId") Integer instructorId, // Captura el nuevo dato
            @RequestParam(value = "claseId") Integer claseId,
            @RequestParam(value = "fechaClase") String fechaClase,
            @RequestParam(value = "registro") String registro,
            RedirectAttributes redirectAttributes) {

        try {
            Asistencia asistencia = new Asistencia();
            asistencia.setId(id);

            Estudiante estudiante = estudianteRepository.findById(estudianteId)
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
            Instructor instructor = instructorRepository.findById(instructorId)
                    .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));
            Clase clase = claseService.listarClases().stream()
                    .filter(c -> c.getId().equals(claseId)).findFirst()
                    .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

            asistencia.setEstudiante(estudiante);
            asistencia.setInstructor(instructor);
            asistencia.setClase(clase);
            asistencia.setFechaClase(LocalDate.parse(fechaClase));
            asistencia.setRegistro(LocalDateTime.parse(registro));

            asistenciaService.guardar(asistencia);

            redirectAttributes.addFlashAttribute("exito", "Asistencia procesada correctamente");
            return "redirect:/asistencias/listar";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error en los datos: " + e.getMessage());
            return "redirect:/asistencias/nuevo";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Asistencia asistencia = asistenciaService.obtenerPorId(id);
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("listaEstudiantes", estudianteRepository.findAll());
        model.addAttribute("listaInstructores", instructorRepository.findAll()); // Cargados para edición
        model.addAttribute("listaClases", claseService.listarClases());
        return "asistencias/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAsistencia(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        asistenciaService.eliminar(id);
        redirectAttributes.addFlashAttribute("exito", "Registro eliminado");
        return "redirect:/asistencias/listar";
    }
}
