package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

/**
 * Entidad Persona, representa a las personas de la escuela kenpo
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "personas")
public abstract class Persona {
    /**
     * Identificador de la persona
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Nombre de la persona
     */
    @Column(nullable = false)
    private String nombres;
    /**
     * Primer Apellido de la persona
     */
    @Column(nullable = false)
    private String apellido1;
    /**
     * Segundo Apellido de la persona
     */
    @Column(nullable = true)
    private String apellido2;
    /**
     *
     */
    @Column(nullable = false)
    private LocalDate fechaNac;
    /**
     *
     */
    @Column(nullable = false)
    private String rut;

}

