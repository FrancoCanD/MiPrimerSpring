package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO DE SALIDA PARA GRADOS
 * ──────────────────────────
 * Contiene los datos que la API retorna cuando se consulta un grado.
 * Se usa como tipo de retorno en los métodos GET, POST y PUT del GradoRestController.
 *
 * Ejemplo de JSON retornado:
 * {
 *   "id": 4,
 *   "nombre": "Verde",
 *   "descripcion": "Nivel intermedio",
 *   "kyuDan": "7° Kyu"
 * }
 *
 * Incluye el "id" generado por la BD, a diferencia del RequestDto que no lo incluye.
 */
@Getter @Setter
public class GradoResponseDto {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String kyuDan;
}
