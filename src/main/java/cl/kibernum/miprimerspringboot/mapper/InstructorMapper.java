package cl.kibernum.miprimerspringboot.mapper;

import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import cl.kibernum.miprimerspringboot.bl.entity.Instructor;
import cl.kibernum.miprimerspringboot.dto.request.InstructorRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.InstructorResponseDto;
import cl.kibernum.miprimerspringboot.repository.GradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstructorMapper {

    @Autowired
    private GradoRepository gradoRepository;

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
