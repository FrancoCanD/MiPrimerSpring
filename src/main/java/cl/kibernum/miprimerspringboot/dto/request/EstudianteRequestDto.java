package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO de entrada para crear o actualizar Estudiantes
 * Este objeto representa los datos que llegan desde una petición Http
 * Se usa un objeto intermedio para no exponer la entidad del modelo interno
 */
@Getter @Setter
public class EstudianteRequestDto extends PersonaRequestDto{

    /*@NotBlank(message = "El grado es necesario")*/
    private GradoRequestDto gradoRequestDto;

    @NotBlank(message = "Debe ir una fecha de ascenso")
    private LocalDate fechaAscenso;

    @NotBlank(message = "Indicar si el estudiante está activo o no")
    private boolean activo;
}
