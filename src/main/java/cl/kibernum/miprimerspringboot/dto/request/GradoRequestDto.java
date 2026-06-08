package cl.kibernum.miprimerspringboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO DE ENTRADA PARA GRADOS
 * ───────────────────────────
 * Contiene los datos que debe enviar el cliente para crear o actualizar un grado.
 * Se usa como @RequestBody en los métodos POST y PUT del GradoRestController.
 *
 * Ejemplo de JSON esperado en el body de la petición:
 * {
 *   "nombre": "Verde",
 *   "descripcion": "Nivel intermedio",
 *   "kyuDan": "7° Kyu"
 * }
 *
 * @NotBlank lanza un error de validación si el campo llega vacío o como espacios.
 */
@Getter @Setter
public class GradoRequestDto {

    @NotBlank(message = "El nombre del cinturón es obligatorio")
    private String nombre;

    private String descripcion; // Opcional

    @NotBlank(message = "El kyu/Dan es obligatorio")
    private String kyuDan;
}
