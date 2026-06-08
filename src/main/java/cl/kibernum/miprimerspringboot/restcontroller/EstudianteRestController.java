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

/**
 * CONTROLADOR REST DE ESTUDIANTES
 * ────────────────────────────────
 * API REST para gestionar alumnos. Acceso: ROL_ADMIN y ROL_INSTRUCTOR.
 *
 * Todos los métodos trabajan con DTOs (no con la entidad Estudiante directamente)
 * para proteger la estructura interna del modelo y controlar qué campos se exponen.
 *
 * Operaciones disponibles:
 *   GET    /api/estudiantes        → lista todos
 *   GET    /api/estudiantes/{id}   → busca por ID
 *   POST   /api/estudiantes        → crea nuevo
 *   PUT    /api/estudiantes/{id}   → actualiza
 *   DELETE /api/estudiantes/{id}   → elimina
 */
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
    @Operation(summary = "Buscar estudiante por Id")
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
    public EstudianteResponseDto actualizarEstudiante(@PathVariable Integer id,
                                                       @RequestBody EstudianteRequestDto estudianteRequestDto) {
        return estudianteService.actualizarEstudianteApi(estudianteRequestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar estudiante")
    public void eliminarEstudiante(@PathVariable Integer id) {
        estudianteService.borrarEstudianteApi(id);
    }
}
