package cl.kibernum.miprimerspringboot.restcontroller;

import cl.kibernum.miprimerspringboot.dto.request.InstructorRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.InstructorResponseDto;
import cl.kibernum.miprimerspringboot.service.InstructorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructores")
public class InstructorRestController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    @Operation(summary = "Listar Instructores")
    public List<InstructorResponseDto> listarInstructores() {
        return instructorService.listarInstructoresApi();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar instructor por Id")
    public InstructorResponseDto instructorPorId(@PathVariable Integer id) {
        return instructorService.instructorPorIdApi(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear Instructor")
    public InstructorResponseDto crearInstructor(@Valid @RequestBody InstructorRequestDto instructorRequestDto) {
        return instructorService.crearInstructorApi(instructorRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Instructor")
    public InstructorResponseDto actualizarInstructor(@PathVariable Integer id,
                                                      @RequestBody InstructorRequestDto instructorRequestDto) {
        return instructorService.actualizarInstructorApi(instructorRequestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar Instructor")
    public void eliminarInstructor(@PathVariable Integer id) {
        instructorService.borrarInstructorApi(id);
    }
}

