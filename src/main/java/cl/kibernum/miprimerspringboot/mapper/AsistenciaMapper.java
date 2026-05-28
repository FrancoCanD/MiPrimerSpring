package cl.kibernum.miprimerspringboot.mapper;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.bl.entity.Clase;
import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.bl.entity.Instructor;
import cl.kibernum.miprimerspringboot.dto.request.AsistenciaRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.AsistenciaResponseDto;
import cl.kibernum.miprimerspringboot.repository.ClaseRepository;
import cl.kibernum.miprimerspringboot.repository.EstudianteRepository;
import cl.kibernum.miprimerspringboot.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AsistenciaMapper {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ClaseRepository claseRepository;

    public Asistencia asistenciaDtoToEntity(AsistenciaRequestDto dto) {
        Asistencia asistencia = new Asistencia();
        asistencia.setRegistro(dto.getRegistro());
        asistencia.setFechaClase(dto.getFechaClase());
        Estudiante estudiante = estudianteRepository.findById(dto.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + dto.getEstudianteId()));
        asistencia.setEstudiante(estudiante);
        Instructor instructor = instructorRepository.findById(dto.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado con id: " + dto.getInstructorId()));
        asistencia.setInstructor(instructor);
        Clase clase = claseRepository.findById(dto.getClaseId())
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con id: " + dto.getClaseId()));
        asistencia.setClase(clase);
        return asistencia;
    }

    public AsistenciaResponseDto asistenciaToResponseDto(Asistencia asistencia) {
        AsistenciaResponseDto dto = new AsistenciaResponseDto();
        dto.setId(asistencia.getId());
        dto.setRegistro(asistencia.getRegistro());
        dto.setFechaClase(asistencia.getFechaClase());
        if (asistencia.getEstudiante() != null) {
            dto.setEstudianteId(asistencia.getEstudiante().getId());
            dto.setEstudianteNombre(asistencia.getEstudiante().getNombres() + " " + asistencia.getEstudiante().getApellido1());
        }
        if (asistencia.getInstructor() != null) {
            dto.setInstructorId(asistencia.getInstructor().getId());
            dto.setInstructorNombre(asistencia.getInstructor().getNombres() + " " + asistencia.getInstructor().getApellido1());
        }
        if (asistencia.getClase() != null) {
            dto.setClaseId(asistencia.getClase().getId());
            dto.setClaseNombre(asistencia.getClase().getTipoClase());
        }
        return dto;
    }

    public void updateAsistenciaEntity(AsistenciaRequestDto dto, Asistencia asistencia) {
        asistencia.setRegistro(dto.getRegistro());
        asistencia.setFechaClase(dto.getFechaClase());
        Estudiante estudiante = estudianteRepository.findById(dto.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + dto.getEstudianteId()));
        asistencia.setEstudiante(estudiante);
        Instructor instructor = instructorRepository.findById(dto.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado con id: " + dto.getInstructorId()));
        asistencia.setInstructor(instructor);
        Clase clase = claseRepository.findById(dto.getClaseId())
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con id: " + dto.getClaseId()));
        asistencia.setClase(clase);
    }
}
