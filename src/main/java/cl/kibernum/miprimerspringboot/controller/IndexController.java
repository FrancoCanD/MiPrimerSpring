package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.service.AsistenciaService;
import cl.kibernum.miprimerspringboot.service.ClaseService;
import cl.kibernum.miprimerspringboot.service.EstudianteService;
import cl.kibernum.miprimerspringboot.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador principal para la página de inicio del sistema
 */
@Controller
public class IndexController {

    @Autowired private EstudianteService estudianteService;
    @Autowired private InstructorService instructorService;
    @Autowired private ClaseService claseService;
    @Autowired private AsistenciaService asistenciaService;

    /**
     * Renderiza el menú principal y envía estadísticas rápidas a la vista
     */
    @GetMapping("/")
    public String index(Model model) {
        // Envíamos contadores dinámicos para hacer el dashboard atractivo
        model.addAttribute("totalEstudiantes", estudianteService.listarEstudiantes().size());
        model.addAttribute("totalInstructores", instructorService.listarInstructores().size());
        model.addAttribute("totalClases", claseService.listarClases().size());
        model.addAttribute("totalAsistencias", asistenciaService.listarTodas().size());

        return "index"; // Busca el archivo index.html en templates/
    }
}
