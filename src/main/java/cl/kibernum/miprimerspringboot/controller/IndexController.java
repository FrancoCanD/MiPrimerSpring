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
 * CONTROLADOR PRINCIPAL — DASHBOARD
 * ─────────────────────────────────
 * Maneja la ruta raíz "/" que muestra el dashboard del sistema con estadísticas
 * en tiempo real: total de estudiantes, instructores, clases y asistencias.
 *
 * Es la primera página que ve el usuario tras iniciar sesión
 * (configurado en SecurityConfig: defaultSuccessUrl("/", true)).
 */
@Controller
public class IndexController {

    @Autowired private EstudianteService estudianteService;
    @Autowired private InstructorService instructorService;
    @Autowired private ClaseService claseService;
    @Autowired private AsistenciaService asistenciaService;

    /**
     * GET /
     * Consulta los contadores de cada entidad y los envía a la vista.
     * .size() cuenta los elementos de cada lista para mostrar el total en las tarjetas del dashboard.
     * La vista indexadm.html accede a estos valores con ${totalEstudiantes}, ${totalInstructores}, etc.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalEstudiantes", estudianteService.listarEstudiantes().size());
        model.addAttribute("totalInstructores", instructorService.listarInstructores().size());
        model.addAttribute("totalClases", claseService.listarClases().size());
        model.addAttribute("totalAsistencias", asistenciaService.listarTodas().size());
        return "indexadm"; // → templates/indexadm.html
    }
}
