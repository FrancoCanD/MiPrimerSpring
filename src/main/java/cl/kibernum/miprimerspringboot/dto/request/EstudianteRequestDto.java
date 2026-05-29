package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Debe seleccionar un grado")
    private Integer idGrado;

    @NotNull(message = "La fecha de ascenso es obligatoria")
    private LocalDate fechaAscenso;

    @NotNull
    private boolean activo;
}
