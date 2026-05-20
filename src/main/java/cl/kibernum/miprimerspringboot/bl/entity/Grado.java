package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad Grado: representa un grado o cinturon dentro de la escuela de Kenpo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "grados")
public class Grado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El nombre del cinturon es obligatorio")
    private String nombre;

    @Column(length = 255) // Corregido: Se elimina nullable = false para coincidir con el script SQL
    private String descripcion;

    @Column(nullable = false, name = "kyu_dan", length = 50) // Corregido: Mapeo explícito snake_case para MySQL
    @NotBlank(message = "El campo Kyu/Dan es obligatorio")
    private String kyuDan;
}
