package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.service.AsistenciaService;
import cl.kibernum.miprimerspringboot.service.ClaseService;
import cl.kibernum.miprimerspringboot.repository.EstudianteRepository; // Reemplaza por tu servicio si tienes uno
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
        return "asistencias/listar";
    }

    // 2. MOSTRAR FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("asistencia", new Asistencia());
        model.addAttribute("listaPersonas", estudianteRepository.findAll());
        model.addAttribute("listaClases", claseService.listarTodas());
        return "asistencias/formulario";
    }

    // 3. PROCESAR GUARDADO / EDICIÓN (Evita errores de conversión de fecha)
    @PostMapping("/guardar")
    public String guardarAsistencia(@ModelAttribute("asistencia") Asistencia asistencia) {
        asistenciaService.guardar(asistencia);
        return "redirect:/asistencias/listar";
    }

    // 4. MOSTRAR FORMULARIO PARA EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Asistencia asistencia = asistenciaService.obtenerPorId(id);
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("listaPersonas", estudianteRepository.findAll());
        model.addAttribute("listaClases", claseService.listarTodas());
        return "asistencias/formulario";
    }

    // 5. ELIMINAR REGISTRO
    @GetMapping("/eliminar/{id}")
    public String eliminarAsistencia(@PathVariable("id") Integer id) {
        asistenciaService.eliminar(id);
        return "redirect:/asistencias/listar";
    }
}
