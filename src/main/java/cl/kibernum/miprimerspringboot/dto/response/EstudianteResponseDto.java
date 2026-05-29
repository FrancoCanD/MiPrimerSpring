package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class EstudianteResponseDto extends PersonaResponseDto{

    private GradoResponseDto gradoResponseDto;
    private LocalDate fechaAscenso;
    private boolean activo;

}
