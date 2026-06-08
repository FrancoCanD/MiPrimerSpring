package cl.kibernum.miprimerspringboot.mapper;

import cl.kibernum.miprimerspringboot.bl.entity.Clase;
import cl.kibernum.miprimerspringboot.dto.request.ClaseRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.ClaseResponseDto;
import org.springframework.stereotype.Component;

/**
 * MAPPER DE CLASE
 * ────────────────
 * Convierte entre la entidad Clase y sus DTOs de entrada/salida.
 * Incluye validaciones de null para evitar NullPointerException si se pasan valores vacíos.
 */
@Component
public class ClaseMapper {

    /**
     * Convierte ClaseRequestDto → Clase.
     * Verifica que el dto no sea null antes de crear la entidad.
     */
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

    /**
     * Convierte Clase → ClaseResponseDto.
     * Verifica que la clase no sea null para no lanzar NullPointerException.
     */
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

    /** Actualiza los campos de una Clase existente con los datos del DTO. */
    public void updateClaseEntity(ClaseRequestDto claseDto, Clase clase) {
        if (claseDto == null || clase == null) {
            return;
        }
        clase.setTipoClase(claseDto.getTipoClase());
        clase.setDescripcion(claseDto.getDescripcion());
        clase.setEstado(claseDto.isEstado());
    }
}
