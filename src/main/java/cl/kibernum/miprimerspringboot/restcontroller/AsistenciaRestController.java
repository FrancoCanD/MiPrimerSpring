package cl.kibernum.miprimerspringboot.restcontroller;

import cl.kibernum.miprimerspringboot.dto.request.AsistenciaRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.AsistenciaResponseDto;
import cl.kibernum.miprimerspringboot.service.AsistenciaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaRestController {

    @Autowired
    private AsistenciaService asistenciaService;

    @GetMapping
    @Operation(summary = "Listar Asistencias")
    public List<AsistenciaResponseDto> listarAsistencias() {
        return asistenciaService.listarAsistenciasApi();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar asistencia por Id")
    public AsistenciaResponseDto asistenciaPorId(@PathVariable Integer id) {
        return asistenciaService.asistenciaPorIdApi(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear Asistencia")
    public AsistenciaResponseDto crearAsistencia(@Valid @RequestBody AsistenciaRequestDto asistenciaRequestDto) {
        return asistenciaService.crearAsistenciaApi(asistenciaRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Asistencia")
    public AsistenciaResponseDto actualizarAsistencia(@PathVariable Integer id,
                                                      @RequestBody AsistenciaRequestDto asistenciaRequestDto) {
        return asistenciaService.actualizarAsistenciaApi(asistenciaRequestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar Asistencia")
    public void eliminarAsistencia(@PathVariable Integer id) {
        asistenciaService.borrarAsistenciaApi(id);
    }
}

