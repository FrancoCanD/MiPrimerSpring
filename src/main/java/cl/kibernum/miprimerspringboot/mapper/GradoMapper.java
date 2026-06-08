package cl.kibernum.miprimerspringboot.mapper;

import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import cl.kibernum.miprimerspringboot.dto.request.GradoRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.GradoResponseDto;
import org.springframework.stereotype.Component;

/**
 * MAPPER DE GRADO
 * ────────────────
 * Convierte entre la entidad Grado y sus DTOs de entrada/salida.
 *
 * ¿QUÉ ES UN MAPPER?
 * Un mapper es una clase que "traduce" objetos de un tipo a otro.
 * Separa la lógica de conversión del controlador y del servicio.
 *
 * ¿POR QUÉ NO EXPONER LA ENTIDAD DIRECTAMENTE?
 * La entidad Grado tiene anotaciones JPA que no deben salir por la API.
 * El DTO solo contiene los campos que el cliente necesita ver o enviar.
 *
 * @Component registra esta clase como un componente de Spring para poder
 * inyectarla con @Autowired donde sea necesario.
 *
 * MÉTODOS:
 *   gradoDtoToEntity   → RequestDto  → Entidad  (para crear)
 *   gradoToResponseDto → Entidad     → ResponseDto (para devolver en API)
 *   updateGradoEntity  → RequestDto  → modifica Entidad existente (para actualizar)
 */
@Component
public class GradoMapper {

    /**
     * Convierte un GradoRequestDto (datos que vienen de la petición) a una entidad Grado.
     * No asigna el ID porque es un grado nuevo: la BD lo generará con AUTO_INCREMENT.
     */
    public Grado gradoDtoToEntity(GradoRequestDto gradoDto) {
        Grado grado = new Grado();
        grado.setNombre(gradoDto.getNombre());
        grado.setDescripcion(gradoDto.getDescripcion());
        grado.setKyuDan(gradoDto.getKyuDan());
        return grado;
    }

    /** Convierte una entidad Grado a un GradoResponseDto para enviar como respuesta JSON. */
    public GradoResponseDto gradoToGradoResponseDto(Grado grado) {
        GradoResponseDto gradoResponseDto = new GradoResponseDto();
        gradoResponseDto.setId(grado.getId());
        gradoResponseDto.setNombre(grado.getNombre());
        gradoResponseDto.setDescripcion(grado.getDescripcion());
        gradoResponseDto.setKyuDan(grado.getKyuDan());
        return gradoResponseDto;
    }

    /**
     * Actualiza una entidad Grado existente con los datos del DTO.
     * Se llama con la entidad ya cargada de la BD y modifica sus campos sin crear un objeto nuevo.
     * Así JPA sabe que debe hacer UPDATE (no INSERT).
     */
    public void updateGradoEntity(GradoRequestDto gradoDto, Grado grado) {
        grado.setNombre(gradoDto.getNombre());
        grado.setDescripcion(gradoDto.getDescripcion());
        grado.setKyuDan(gradoDto.getKyuDan());
    }
}
