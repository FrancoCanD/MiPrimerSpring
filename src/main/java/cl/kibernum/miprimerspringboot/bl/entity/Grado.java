package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ENTIDAD: GRADO (CINTURÓN)
 * ──────────────────────────
 * Representa un nivel en la escala jerárquica de Kenpo.
 * Ejemplos: Blanco (10° Kyu), Negro (1° Dan), etc.
 *
 * Es referenciada por Estudiante e Instructor a través de @ManyToOne.
 * Eso significa que un mismo Grado puede estar asignado a muchos alumnos e instructores.
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

    /** Nombre del cinturón: "Blanco", "Amarillo", "Negro", etc. */
    @Column(nullable = false, length = 50)
    @NotBlank(message = "El nombre del cinturon es obligatorio")
    private String nombre;

    /**
     * Descripción y requisitos técnicos del grado.
     * No tiene nullable = false porque en el SQL también es nullable (valor por defecto).
     */
    @Column(length = 255)
    private String descripcion;

    /**
     * Clasificación del grado según el sistema Kyu/Dan de artes marciales.
     * Kyu = grados de color (de principiante a marrón): "10° Kyu", "9° Kyu", etc.
     * Dan = grados de cinturón negro: "1° Dan", "2° Dan", etc.
     *
     * name = "kyu_dan" mapea al nombre exacto de la columna en MySQL (snake_case).
     * Sin este mapeo, JPA buscaría una columna llamada "kyuDan" y fallaría.
     */
    @Column(nullable = false, name = "kyu_dan", length = 50)
    @NotBlank(message = "El campo Kyu/Dan es obligatorio")
    private String kyuDan;
}
