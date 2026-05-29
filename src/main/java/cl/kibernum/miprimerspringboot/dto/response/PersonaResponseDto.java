package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public abstract class PersonaResponseDto {
    private Integer id;
    private String nombres;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNac;
    private String rut;


}
