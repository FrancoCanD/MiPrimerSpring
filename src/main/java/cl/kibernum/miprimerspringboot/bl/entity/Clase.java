package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad Clase, represnta las clases impartidas en escuela Kenpo
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name= "clases")
public class Clase {
    /**
     * ID de clase impartida
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Tipo clase: clasificacaión de la clase
     * Ejemplo: Sparring, ténica, Acondicionamiento físico, etc.
     */
    @Column(nullable = false)
    private String tipoClase;
    /**
     * Descripción específica de la clase impartida
     * Ejemplo: golpe de puño
     */
    @Column(nullable = false)
    private String descripcion;
    /**Indica el estado de la clase
     * Ejemplo: Activa
     */
    @Column(nullable = false)
    private boolean estado;
}
