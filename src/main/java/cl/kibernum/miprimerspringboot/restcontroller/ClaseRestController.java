package cl.kibernum.miprimerspringboot.restcontroller;

import cl.kibernum.miprimerspringboot.dto.request.ClaseRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.ClaseResponseDto;
import cl.kibernum.miprimerspringboot.service.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clases")
public class ClaseRestController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    @Operation(summary = "Listar Clases")
    public List<ClaseResponseDto> listarClase() {
        return claseService.listarClasesApi();
    }

    @GetMapping("/{id}")
    @Operation(summary = "buscar clase por Id")
    public ClaseResponseDto clasePorId(@PathVariable Integer id) {
        return claseService.clasePorIdApi(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear Clase")
    public ClaseResponseDto crearClase(@Valid @RequestBody ClaseRequestDto claseRequestDto) {
        return claseService.crearClaseApi(claseRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar clase")
    public ClaseResponseDto actualizarClase(@PathVariable Integer id, @RequestBody ClaseRequestDto claseRequestDto) {
        return claseService.actualizarClaseApi(claseRequestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar clase")
    public void eliminarClase(@PathVariable Integer id) {
        claseService.borrarClaseApi(id);
    }
}
