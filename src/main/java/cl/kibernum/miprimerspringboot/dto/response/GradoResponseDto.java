package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GradoResponseDto {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String kyuDan;
}
