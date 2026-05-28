package cl.kibernum.miprimerspringboot.mapper;

import cl.kibernum.miprimerspringboot.bl.entity.Clase;
import cl.kibernum.miprimerspringboot.dto.request.ClaseRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.ClaseResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ClaseMapper {

    public Clase claseDtoToEntity(ClaseRequestDto claseDto) {
        if (claseDto == null) {
            return null;
        }
        Clase clase = new Clase();
        clase.setTipoClase(claseDto.getTipoClase());
        clase.setDescripcion(claseDto.getDescripcion());
        clase.setEstado(claseDto.isEstado());
        return clase;
    }

    public ClaseResponseDto claseToClaseResponseDto(Clase clase) {
        if (clase == null) {
            return null;
        }
        ClaseResponseDto claseResponseDto = new ClaseResponseDto();
        claseResponseDto.setId(clase.getId());
        claseResponseDto.setTipoClase(clase.getTipoClase());
        claseResponseDto.setDescripcion(clase.getDescripcion());
        claseResponseDto.setEstado(clase.isEstado());
        return claseResponseDto;
    }

    public void updateClaseEntity(ClaseRequestDto claseDto, Clase clase) {
        if (claseDto == null || clase == null) {
            return;
        }
        clase.setTipoClase(claseDto.getTipoClase());
        clase.setDescripcion(claseDto.getDescripcion());
        clase.setEstado(claseDto.isEstado());
    }
}
