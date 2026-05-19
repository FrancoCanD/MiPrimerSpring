package cl.kibernum.miprimerspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index"; // Busca el archivo index.html dentro de la carpeta templates
    }
}
