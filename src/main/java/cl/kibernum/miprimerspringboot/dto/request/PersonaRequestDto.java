package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO BASE DE ENTRADA PARA PERSONAS
 * ───────────────────────────────────
 * Clase abstracta que contiene los campos comunes a EstudianteRequestDto e InstructorRequestDto.
 *
 * ¿POR QUÉ ES ABSTRACTA?
 * Igual que la entidad Persona, no tiene sentido crear una "persona genérica".
 * Las peticiones siempre crearán un Estudiante o un Instructor específico.
 * Al extender esta clase, los DTOs hijos heredan automáticamente todos estos campos.
 *
 * PATRÓN DTO (Data Transfer Object):
 * Separa los datos que viajan por la red del modelo interno de la BD.
 *   - Ventaja: puedes agregar/quitar campos de la API sin tocar las entidades.
 *   - Ventaja: las anotaciones de validación (@NotBlank, @NotNull) están aquí,
 *              no contaminan la entidad de negocio.
 */
@Getter @Setter
public abstract class PersonaRequestDto {

    /** Nombres de la persona. No puede ser nulo ni vacío. */
    @NotBlank(message = "Debe contener al menos un nombre")
    private String nombres;

    /** Primer apellido obligatorio. */
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido1;

    /** Segundo apellido opcional. Sin @NotBlank para permitir null. */
    private String apellido2;

    /** Fecha de nacimiento. @NotNull permite null en el String pero fuerza a enviar la fecha. */
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNac;

    /** RUT único del postulante. Validado como no vacío. */
    @NotBlank(message = "El rut es obligatorio")
    private String rut;
}
