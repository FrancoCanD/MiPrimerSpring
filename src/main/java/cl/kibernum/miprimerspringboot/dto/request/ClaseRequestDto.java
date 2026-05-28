package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de entrada para crear o actualizar una Clase
 * Este objeto representa los datos que llegan desde una petición Http
 * Se usa un objeto intermedio para no exponer la entidad del modelo interno
 */

@Getter
@Setter
public class ClaseRequestDto {

    @NotBlank(message = "El tipo de clase es obligatorio")
    private String tipoClase;

    private String descripcion; //preguntar si debe ser igual a la Entidad Clase, con notblank

    private boolean estado;
}