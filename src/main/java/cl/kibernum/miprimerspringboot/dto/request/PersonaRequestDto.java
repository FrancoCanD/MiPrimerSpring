package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO de entrada para crear o actualizar Personas
 * Este objeto representa los datos que llegan desde una petición Http
 * Se usa un objeto intermedio para no exponer la entidad del modelo interno
 */
@Getter @Setter
public abstract class PersonaRequestDto {

    @NotBlank(message = "Debe contener al menos un mombre")
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido1;

    private String apellido2;

    @NotBlank(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNac;

    @NotBlank(message = "El rut es obligatorio")
    private String rut;
}

