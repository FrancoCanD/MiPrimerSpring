package cl.kibernum.miprimerspringboot.mapper;

import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import cl.kibernum.miprimerspringboot.dto.request.EstudianteRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.EstudianteResponseDto;
import cl.kibernum.miprimerspringboot.dto.response.GradoResponseDto;
import cl.kibernum.miprimerspringboot.service.serviceimpl.GradoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MAPPER DE ESTUDIANTE
 * ─────────────────────
 * Convierte entre la entidad Estudiante y sus DTOs.
 *
 * PARTICULARIDAD: necesita acceder a GradoServiceImpl para cargar
 * el objeto Grado completo desde la BD usando el idGrado que viene en el DTO.
 * El DTO solo trae el ID del grado; el mapper lo convierte a la entidad real.
 *
 * También usa GradoMapper para convertir el Grado a GradoResponseDto
 * al armar el EstudianteResponseDto (anidamiento de DTOs).
 */
@Component
public class EstudianteMapper {

    @Autowired
    private GradoMapper gradoMapper;

    @Autowired
    private GradoServiceImpl gradoServiceImpl;

    /**
     * Convierte EstudianteRequestDto → Estudiante.
     * El campo idGrado del DTO se convierte a un objeto Grado completo
     * buscando en la BD con gradoServiceImpl.gradoPorId().
     */
    public Estudiante estudianteDtoToEntity(EstudianteRequestDto estudianteDto) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombres(estudianteDto.getNombres());
        estudiante.setApellido1(estudianteDto.getApellido1());
        estudiante.setApellido2(estudianteDto.getApellido2());
        estudiante.setFechaNac(estudianteDto.getFechaNac());
        estudiante.setRut(estudianteDto.getRut());
        // Buscamos el Grado completo en la BD por el ID que llegó en el DTO
        Grado grado = gradoServiceImpl.gradoPorId(estudianteDto.getIdGrado())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));
        estudiante.setGrado(grado);
        estudiante.setFechaAscenso(estudianteDto.getFechaAscenso());
        estudiante.setActivo(estudianteDto.isActivo());
        return estudiante;
    }

    /**
     * Convierte Estudiante → EstudianteResponseDto.
     * El grado se convierte a GradoResponseDto (DTO anidado dentro del DTO de estudiante).
     * Así la respuesta JSON incluye los datos del grado sin exponer la entidad interna.
     */
    public EstudianteResponseDto estudianteToEstudianteResponseDto(Estudiante estudiante) {
        EstudianteResponseDto estudianteResponseDto = new EstudianteResponseDto();
        estudianteResponseDto.setId(estudiante.getId());
        estudianteResponseDto.setNombres(estudiante.getNombres());
        estudianteResponseDto.setApellido1(estudiante.getApellido1());
        estudianteResponseDto.setApellido2(estudiante.getApellido2());
        estudianteResponseDto.setFechaNac(estudiante.getFechaNac());
        estudianteResponseDto.setRut(estudiante.getRut());
        // Convierte la entidad Grado a un DTO anidado
        GradoResponseDto gradoResponseDto = gradoMapper.gradoToGradoResponseDto(estudiante.getGrado());
        estudianteResponseDto.setGradoResponseDto(gradoResponseDto);
        estudianteResponseDto.setFechaAscenso(estudiante.getFechaAscenso());
        estudianteResponseDto.setActivo(estudiante.isActivo());
        return estudianteResponseDto;
    }

    /** Actualiza una entidad Estudiante existente con los datos del DTO (para PUT en la API). */
    public void updateEstudianteEntity(EstudianteRequestDto estudianteDto, Estudiante estudiante) {
        estudiante.setNombres(estudianteDto.getNombres());
        estudiante.setApellido1(estudianteDto.getApellido1());
        estudiante.setApellido2(estudianteDto.getApellido2());
        estudiante.setFechaNac(estudianteDto.getFechaNac());
        estudiante.setRut(estudianteDto.getRut());
        Grado grado = gradoServiceImpl.gradoPorId(estudianteDto.getIdGrado())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));
        estudiante.setGrado(grado);
        estudiante.setFechaAscenso(estudianteDto.getFechaAscenso());
        estudiante.setActivo(estudianteDto.isActivo());
    }
}
