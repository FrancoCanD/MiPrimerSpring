package cl.kibernum.miprimerspringboot.bl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad Clase, representa las clases impartidas en escuela Kenpo
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

    @NotBlank(message = "El tipo de clase es obligatorio")
    @Column(nullable = false, name = "tipo_clase", length = 100) // Corregido: Mapeo explícito
    private String tipoClase;

    @NotBlank(message = "La descripción de la clase es obligatoria")
    @Column(nullable = false, length = 300)
    private String descripcion;

    @Column(nullable = false)
    private boolean estado;

}
