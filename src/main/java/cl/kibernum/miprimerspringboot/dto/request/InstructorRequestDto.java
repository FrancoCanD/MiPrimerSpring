package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO de entrada para crear o actualizar Instructores
 * Este objeto representa los datos que llegan desde una petición Http
 * Se usa un objeto intermedio para no exponer la entidad del modelo interno
 */
@Getter @Setter
public class InstructorRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;

    @NotBlank(message = "El primer apellido es obligatorio")
    private String apellido1;

    private String apellido2;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNac;

    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @NotNull(message = "El grado es obligatorio")
    private Integer gradoId;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private boolean activo;

    private Integer anosExperiencia;
}

