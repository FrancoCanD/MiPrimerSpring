package cl.kibernum.miprimerspringboot.mapper;

import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import cl.kibernum.miprimerspringboot.bl.entity.Instructor;
import cl.kibernum.miprimerspringboot.dto.request.InstructorRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.InstructorResponseDto;
import cl.kibernum.miprimerspringboot.repository.GradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MAPPER DE INSTRUCTOR
 * ─────────────────────
 * Convierte entre la entidad Instructor y sus DTOs.
 * Usa GradoRepository para cargar el Grado completo a partir del gradoId del DTO.
 */
@Component
public class InstructorMapper {

    @Autowired
    private GradoRepository gradoRepository;

    /**
     * Convierte InstructorRequestDto → Instructor.
     * El gradoId del DTO se convierte al objeto Grado completo buscando en la BD.
     */
    public Instructor instructorDtoToEntity(InstructorRequestDto dto) {
        Instructor instructor = new Instructor();
        instructor.setNombres(dto.getNombres());
        instructor.setApellido1(dto.getApellido1());
        instructor.setApellido2(dto.getApellido2());
        instructor.setFechaNac(dto.getFechaNac());
        instructor.setRut(dto.getRut());
        instructor.setEspecialidad(dto.getEspecialidad());
        instructor.setFechaInicio(dto.getFechaInicio());
        instructor.setActivo(dto.isActivo());
        instructor.setAnosExperiencia(dto.getAnosExperiencia());
        Grado grado = gradoRepository.findById(dto.getGradoId())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con id: " + dto.getGradoId()));
        instructor.setGrado(grado);
        return instructor;
    }

    /**
     * Convierte Instructor → InstructorResponseDto.
     * El grado se "aplana" en dos campos separados: gradoId y gradoNombre.
     * La verificación null evita errores si por alguna razón el grado no está cargado.
     */
    public InstructorResponseDto instructorToResponseDto(Instructor instructor) {
        InstructorResponseDto dto = new InstructorResponseDto();
        dto.setId(instructor.getId());
        dto.setNombres(instructor.getNombres());
        dto.setApellido1(instructor.getApellido1());
        dto.setApellido2(instructor.getApellido2());
        dto.setFechaNac(instructor.getFechaNac());
        dto.setRut(instructor.getRut());
        dto.setEspecialidad(instructor.getEspecialidad());
        dto.setFechaInicio(instructor.getFechaInicio());
        dto.setActivo(instructor.isActivo());
        dto.setAnosExperiencia(instructor.getAnosExperiencia());
        if (instructor.getGrado() != null) {
            dto.setGradoId(instructor.getGrado().getId());
            dto.setGradoNombre(instructor.getGrado().getNombre());
        }
        return dto;
    }

    /** Actualiza una entidad Instructor existente con los nuevos datos del DTO. */
    public void updateInstructorEntity(InstructorRequestDto dto, Instructor instructor) {
        instructor.setNombres(dto.getNombres());
        instructor.setApellido1(dto.getApellido1());
        instructor.setApellido2(dto.getApellido2());
        instructor.setFechaNac(dto.getFechaNac());
        instructor.setRut(dto.getRut());
        instructor.setEspecialidad(dto.getEspecialidad());
        instructor.setFechaInicio(dto.getFechaInicio());
        instructor.setActivo(dto.isActivo());
        instructor.setAnosExperiencia(dto.getAnosExperiencia());
        Grado grado = gradoRepository.findById(dto.getGradoId())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con id: " + dto.getGradoId()));
        instructor.setGrado(grado);
    }
}
