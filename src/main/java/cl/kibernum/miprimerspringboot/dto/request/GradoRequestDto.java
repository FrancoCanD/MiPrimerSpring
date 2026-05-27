package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de entrada para crear o actualizar Grados
 * Este objeto representa los datos que llegan desde una petición Http
 * Se usa un objeto intermedio para no exponer la entidad del modelo interno
 */
@Getter @Setter
public class GradoRequestDto {

    @NotBlank(message = "El nombre del cinturón es obligatorio")
    private String nombre;

    private String descripcion;

    @NotBlank(message = "El kyu/Dan es obligatorio")
    private String kyuDan;
}
