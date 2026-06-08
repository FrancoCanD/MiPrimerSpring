package cl.kibernum.miprimerspringboot.controller;

import cl.kibernum.miprimerspringboot.bl.entity.Usuario;
import cl.kibernum.miprimerspringboot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/inicio")
    public String inicio(Authentication auth, Model model) {
        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "inicio";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "auth/acceso-denegado";
    }
}
