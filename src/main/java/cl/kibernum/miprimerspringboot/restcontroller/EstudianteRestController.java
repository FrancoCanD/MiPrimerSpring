package cl.kibernum.miprimerspringboot.restcontroller;

import cl.kibernum.miprimerspringboot.dto.request.EstudianteRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.EstudianteResponseDto;
import cl.kibernum.miprimerspringboot.service.EstudianteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteRestController {
    @Autowired
    EstudianteService estudianteService;

    @GetMapping
    @Operation(summary = "Listar Estudiantes")
    public List<EstudianteResponseDto> listarEstudiante() {
        return estudianteService.listarEstudiantesApi();
    }

    @GetMapping("/{id}")
    @Operation(summary = "buscar estudiante por Id")
    public EstudianteResponseDto estudiantePorId(@PathVariable Integer id) {
        return estudianteService.estudiantePorIdApi(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear Estudiante")
    public EstudianteResponseDto crearEstudiante(@Valid @RequestBody EstudianteRequestDto estudianteRequestDto) {
        return estudianteService.crearEstudianteApi(estudianteRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estudiante")
    public EstudianteResponseDto actualizarEstudiante(@PathVariable Integer id, @RequestBody EstudianteRequestDto estudianteRequestDto) {
        return estudianteService.actualizarEstudianteApi(estudianteRequestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar estudiante")
    public void eliminarEstudiante(@PathVariable Integer id) {
        estudianteService.borrarEstudianteApi(id);
    }
}
