package cl.kibernum.miprimerspringboot.restcontroller;

import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import cl.kibernum.miprimerspringboot.dto.request.GradoRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.GradoResponseDto;
import cl.kibernum.miprimerspringboot.service.GradoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grados")
public class GradoRestController {
    @Autowired
    private GradoService gradoService;

    @GetMapping
    @Operation(summary = "Listar Grados")
    public List<GradoResponseDto> listarGrado() {
        return gradoService.listarGradosApi();
    }
    @GetMapping("/{id}")
    @Operation(summary = "buscar grado por Id")
    public GradoResponseDto gradoPorId(@PathVariable Integer id) {
        return gradoService.gradoPorIdApi(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear Grado")
    public GradoResponseDto crearGrado(@Valid @RequestBody GradoRequestDto gradoRequestDto) {
        return gradoService.crearGradoApi(gradoRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar grado")
    public GradoResponseDto actualizarGrado(@PathVariable Integer id, @RequestBody GradoRequestDto gradoRequestDto) {
        return gradoService.actualizarGradoApi(gradoRequestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar grado")
    public void eliminarGrado(@PathVariable Integer id) {
        gradoService.borrarGradoApi(id);
    }
}
