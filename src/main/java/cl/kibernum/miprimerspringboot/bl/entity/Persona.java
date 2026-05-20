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
 * Entidad Persona, representa a las personas de la escuela kenpo
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Constructor vacío protegido para JPA
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Evita errores de comparación en clases hijas
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "personas")
public abstract class Persona {

    /**
     * Identificador de la persona
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // Usado para la comparación segura de entidades
    private Integer id;

    /**
     * Nombre de la persona
     */
    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombres;

    /**
     * Primer Apellido de la persona
     */
    @NotBlank
    @Column(nullable = false, length = 100)
    private String apellido1;

    /**
     * Segundo Apellido de la persona
     */
    private String apellido2; // Se elimina nullable=true por ser el comportamiento por defecto

    /**
     * Fecha de nacimiento de la persona
     */
    @Column(nullable = false, name = "fecha_nac") // Buenas prácticas: Mapeo explícito snake_case para MySQL
    private LocalDate fechaNac;

    /**
     * RUT único de la persona
     */
    @NotBlank
    @Column(nullable = false, unique = true, length = 12) //Restricción de unicidad para el RUT
    private String rut;
}
