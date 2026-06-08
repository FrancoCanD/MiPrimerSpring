package cl.kibernum.miprimerspringboot.restcontroller;

import cl.kibernum.miprimerspringboot.dto.request.GradoRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.GradoResponseDto;
import cl.kibernum.miprimerspringboot.service.GradoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLADOR REST DE GRADOS
 * ───────────────────────────
 * Expone la API REST para gestionar grados/cinturones.
 * Acceso: solo ROL_ADMIN (configurado en SecurityConfig, cadena apiSecurityFilterChain).
 *
 * DIFERENCIA entre @Controller y @RestController:
 *   @Controller     → devuelve el nombre de una vista HTML (Thymeleaf)
 *   @RestController → devuelve datos serializados como JSON directamente en el body HTTP
 *                     (equivale a @Controller + @ResponseBody en cada método)
 *
 * Rutas de esta API:
 *   GET    /api/grados        → listar todos
 *   GET    /api/grados/{id}   → buscar por ID
 *   POST   /api/grados        → crear nuevo (body: JSON con GradoRequestDto)
 *   PUT    /api/grados/{id}   → actualizar existente
 *   DELETE /api/grados/{id}   → eliminar
 *
 * @Operation (de SpringDoc/Swagger) agrega descripción legible en la documentación /swagger-ui.html
 */
@RestController
@RequestMapping("/api/grados")
public class GradoRestController {

    @Autowired
    private GradoService gradoService;

    /** GET /api/grados → retorna lista de grados como JSON. */
    @GetMapping
    @Operation(summary = "Listar Grados")
    public List<GradoResponseDto> listarGrado() {
        return gradoService.listarGradosApi();
    }

    /** GET /api/grados/{id} → retorna un grado por ID. */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar grado por Id")
    public GradoResponseDto gradoPorId(@PathVariable Integer id) {
        return gradoService.gradoPorIdApi(id);
    }

    /**
     * POST /api/grados → crea un nuevo grado.
     * @RequestBody convierte el JSON del body de la petición al objeto GradoRequestDto.
     * @Valid activa las validaciones (@NotBlank, etc.) del DTO.
     * @ResponseStatus(CREATED) retorna el código HTTP 201 en vez del 200 por defecto.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear Grado")
    public GradoResponseDto crearGrado(@Valid @RequestBody GradoRequestDto gradoRequestDto) {
        return gradoService.crearGradoApi(gradoRequestDto);
    }

    /**
     * PUT /api/grados/{id} → actualiza un grado existente.
     * Recibe el ID en la URL y los nuevos datos en el body JSON.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar grado")
    public GradoResponseDto actualizarGrado(@PathVariable Integer id,
                                             @RequestBody GradoRequestDto gradoRequestDto) {
        return gradoService.actualizarGradoApi(gradoRequestDto, id);
    }

    /**
     * DELETE /api/grados/{id} → elimina un grado.
     * @ResponseStatus(NO_CONTENT) retorna HTTP 204 (sin cuerpo en la respuesta).
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar grado")
    public void eliminarGrado(@PathVariable Integer id) {
        gradoService.borrarGradoApi(id);
    }
}
