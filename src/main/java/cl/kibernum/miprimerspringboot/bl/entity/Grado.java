package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad Grado: representa un grado o cinturon dentro de la escuela de Kenpo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "grados")
public class Grado {
    /**
     * Identificador único del grado
     * Se genera automáticamente en la BD
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Nombre del cinturón o grado
     * Ejemplo: Blanco, Amarillo, Naranjo, etc.
     */
    @Column(nullable = false)
    @NotBlank(message = "El nombre del cinturon es obligatorio")
    private String nombre;
    /**
     * Descripción del Grado
     * Ejemplo: Grado inicial del estudiante
     */
    @Column(nullable = false)
    private String descripcion;
    /**
     * Kyu o Dan correspondiente al grado
     * Ejemplo: 9° Kyu o 5° Dan
     */
    @Column
    @NotNull
    private String kyuDan;


}
