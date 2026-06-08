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

/**
 * MAPPER DE ASISTENCIA
 * ─────────────────────
 * Convierte entre la entidad Asistencia y sus DTOs.
 *
 * Es el mapper más complejo porque Asistencia tiene TRES relaciones:
 *   - estudianteId → necesita cargar el Estudiante desde EstudianteRepository
 *   - instructorId → necesita cargar el Instructor desde InstructorRepository
 *   - claseId      → necesita cargar la Clase desde ClaseRepository
 *
 * Al convertir a ResponseDto, "aplana" las relaciones mostrando
 * el nombre completo del estudiante e instructor en lugar de solo el ID.
 */
@Component
public class AsistenciaMapper {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ClaseRepository claseRepository;

    /**
     * Convierte AsistenciaRequestDto → Asistencia.
     * Los IDs de estudiante, instructor y clase se convierten a entidades completas.
     * Si algún ID no existe, orElseThrow lanza RuntimeException con mensaje descriptivo.
     */
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

    /**
     * Convierte Asistencia → AsistenciaResponseDto.
     * Las relaciones se "aplanan":
     *   - estudiante → estudianteId + estudianteNombre (ej: "Juan Pérez")
     *   - instructor → instructorId + instructorNombre (ej: "Carlos Muñoz")
     *   - clase      → claseId + claseNombre (ej: "Kata")
     *
     * La verificación null evita NullPointerException si alguna relación no está cargada.
     */
    public AsistenciaResponseDto asistenciaToResponseDto(Asistencia asistencia) {
        AsistenciaResponseDto dto = new AsistenciaResponseDto();
        dto.setId(asistencia.getId());
        dto.setRegistro(asistencia.getRegistro());
        dto.setFechaClase(asistencia.getFechaClase());

        if (asistencia.getEstudiante() != null) {
            dto.setEstudianteId(asistencia.getEstudiante().getId());
            // Concatena nombres y apellido para que la API muestre el nombre completo
            dto.setEstudianteNombre(asistencia.getEstudiante().getNombres() + " "
                    + asistencia.getEstudiante().getApellido1());
        }
        if (asistencia.getInstructor() != null) {
            dto.setInstructorId(asistencia.getInstructor().getId());
            dto.setInstructorNombre(asistencia.getInstructor().getNombres() + " "
                    + asistencia.getInstructor().getApellido1());
        }
        if (asistencia.getClase() != null) {
            dto.setClaseId(asistencia.getClase().getId());
            dto.setClaseNombre(asistencia.getClase().getTipoClase());
        }
        return dto;
    }

    /** Actualiza una Asistencia existente con los datos del DTO. */
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
