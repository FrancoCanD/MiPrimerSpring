package cl.kibernum.miprimerspringboot.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClaseResponseDto {
    private Integer id;
    private String tipoClase;
    private String descripcion;
    public Boolean estado;
}