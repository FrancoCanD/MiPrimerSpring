package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO DE SALIDA PARA CLASES
 * ──────────────────────────
 * Datos que la API retorna al consultar una clase.
 * Incluye el "id" generado (que el RequestDto no tenía).
 *
 * Ejemplo de JSON retornado:
 * {
 *   "id": 2,
 *   "tipoClase": "Kata",
 *   "descripcion": "Kata Pinan Shodan",
 *   "estado": true
 * }
 */
@Getter @Setter
public class ClaseResponseDto {
    private Integer id;
    private String tipoClase;
    private String descripcion;
    /** Boolean con mayúscula (objeto) para que pueda ser null en caso de no establecerse. */
    public Boolean estado;
}
