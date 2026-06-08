package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Persona;
import cl.kibernum.miprimerspringboot.bl.entity.Usuario;
import cl.kibernum.miprimerspringboot.repository.PersonaRepository;
import cl.kibernum.miprimerspringboot.repository.UsuarioRepository;
import cl.kibernum.miprimerspringboot.service.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UsuarioDetailsService usuarioService;
    @Autowired private PersonaRepository personaRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<Persona> personasDisponibles = personaRepository.findAll().stream()
                .filter(p -> !usuarioRepository.existsByPersonaId(p.getId()))
                .toList();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("personasDisponibles", personasDisponibles);
        return "admin/usuarios";
    }

    @PostMapping("/usuarios/{id}/vincular-persona")
    public String vincularPersona(@PathVariable Integer id, @RequestParam Integer personaId) {
        usuarioService.vincularPersona(id, personaId);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/{id}/desvincular-persona")
    public String desvincularPersona(@PathVariable Integer id) {
        usuarioService.desvincularPersona(id);
        return "redirect:/admin/usuarios";
    }
}
