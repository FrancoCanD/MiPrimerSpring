package cl.kibernum.miprimerspringboot.mapper;

import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import cl.kibernum.miprimerspringboot.dto.request.GradoRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.GradoResponseDto;
import org.springframework.stereotype.Component;

@Component
public class GradoMapper {

    public Grado gradoDtoToEntity(GradoRequestDto gradoDto) {
        Grado grado = new Grado();
        grado.setNombre(gradoDto.getNombre());
        grado.setDescripcion(gradoDto.getDescripcion());
        grado.setKyuDan(gradoDto.getKyuDan());
        return grado;
    }

    public GradoResponseDto gradoToGradoResponseDto(Grado grado) {
        GradoResponseDto gradoResponseDto = new GradoResponseDto();
        gradoResponseDto.setId(grado.getId());
        gradoResponseDto.setNombre(grado.getNombre());
        gradoResponseDto.setDescripcion(grado.getDescripcion());
        gradoResponseDto.setKyuDan(grado.getKyuDan());
        return gradoResponseDto;
    }

    public void updateGradoEntity(GradoRequestDto gradoDto, Grado grado) {
        grado.setNombre(gradoDto.getNombre());
        grado.setDescripcion(gradoDto.getDescripcion());
        grado.setKyuDan(gradoDto.getKyuDan());
    }
}
