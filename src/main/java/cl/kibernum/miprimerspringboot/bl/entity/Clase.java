package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ENTIDAD: CLASE
 * ───────────────
 * Representa un tipo de clase impartida en la academia.
 * Ejemplos: "Técnica - Golpe de puño frontal", "Kata - Pinan Shodan", "Sparring".
 *
 * Cada Asistencia registra a qué Clase asistió un estudiante.
 * Una Clase puede aparecer en muchas asistencias (@OneToMany implícito desde Asistencia).
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "clases")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Categoría de la clase: "Técnica", "Kata", "Sparring", etc.
     * name = "tipo_clase" mapea al nombre correcto en la tabla MySQL.
     */
    @NotBlank(message = "El tipo de clase es obligatorio")
    @Column(nullable = false, name = "tipo_clase", length = 100)
    private String tipoClase;

    /** Descripción detallada del contenido de la clase. */
    @NotBlank(message = "La descripción de la clase es obligatoria")
    @Column(nullable = false, length = 300)
    private String descripcion;

    /**
     * Estado de la clase: true = activa (se puede usar en asistencias), false = inactiva.
     * boolean primitivo porque siempre tiene valor (no puede ser null en la BD).
     */
    @Column(nullable = false)
    private boolean estado;
}
