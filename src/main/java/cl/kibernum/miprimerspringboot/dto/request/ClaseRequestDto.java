package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO DE ENTRADA PARA CLASES
 * ───────────────────────────
 * Datos que debe enviar el cliente para crear o actualizar una clase.
 *
 * Ejemplo de JSON esperado:
 * {
 *   "tipoClase": "Kata",
 *   "descripcion": "Kata Pinan Shodan",
 *   "estado": true
 * }
 */
@Getter
@Setter
public class ClaseRequestDto {

    @NotBlank(message = "El tipo de clase es obligatorio")
    private String tipoClase;

    private String descripcion; // Opcional según decisión del equipo

    /** true = clase activa, false = inactiva. Valor boolean (primitivo), siempre requerido. */
    private boolean estado;
}
