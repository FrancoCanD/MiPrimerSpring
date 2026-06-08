package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

/**
 * ENTIDAD BASE: PERSONA
 * ──────────────────────
 * Representa a cualquier persona vinculada a la escuela (estudiante o instructor).
 * Es una clase ABSTRACTA: no se pueden crear objetos de tipo Persona directamente.
 * Solo existen Estudiantes e Instructores concretos.
 *
 * HERENCIA EN JPA — InheritanceType.JOINED:
 * Esta estrategia crea UNA tabla por clase en la base de datos:
 *   - Tabla "personas"    → campos comunes (id, nombres, apellido1, rut, etc.)
 *   - Tabla "estudiantes" → campos propios del estudiante (grado_id, fecha_ascenso...)
 *   - Tabla "instructores"→ campos propios del instructor (especialidad, fecha_inicio...)
 *
 * Cuando JPA consulta un Estudiante, hace un JOIN entre personas y estudiantes.
 * Esto evita duplicar los campos comunes en cada tabla hija.
 *
 * LOMBOK:
 *   @Getter / @Setter       → genera métodos get y set automáticamente
 *   @AllArgsConstructor     → constructor con todos los campos
 *   @NoArgsConstructor(PROTECTED) → constructor vacío solo para uso interno de JPA
 *   @EqualsAndHashCode      → genera equals() y hashCode() solo con el campo @Include
 *                             Evita errores de comparación entre clases hijas (Estudiante vs Instructor)
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "personas")
public abstract class Persona {

    /**
     * Identificador único auto-generado por MySQL (AUTO_INCREMENT).
     * @EqualsAndHashCode.Include indica que solo este campo se usa
     * para comparar si dos personas son iguales.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    /** Nombre(s) de la persona. No puede estar vacío. */
    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombres;

    /** Primer apellido. Obligatorio. */
    @NotBlank
    @Column(nullable = false, length = 100)
    private String apellido1;

    /** Segundo apellido. Puede ser null (campo opcional). */
    private String apellido2;

    /**
     * Fecha de nacimiento.
     * name = "fecha_nac" mapea el atributo Java al nombre exacto de la columna MySQL.
     * Se usa LocalDate (solo fecha, sin hora): 2009-01-01
     */
    @Column(nullable = false, name = "fecha_nac")
    private LocalDate fechaNac;

    /**
     * RUT chileno único de la persona.
     * unique = true crea un índice UNIQUE en la BD para evitar RUTs duplicados.
     */
    @NotBlank
    @Column(nullable = false, unique = true, length = 12)
    private String rut;
}
