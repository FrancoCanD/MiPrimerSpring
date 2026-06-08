package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Instructor;
import cl.kibernum.miprimerspringboot.dto.request.InstructorRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.InstructorResponseDto;
import cl.kibernum.miprimerspringboot.mapper.InstructorMapper;
import cl.kibernum.miprimerspringboot.repository.InstructorRepository;
import cl.kibernum.miprimerspringboot.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTACIÓN DEL SERVICIO DE INSTRUCTORES
 * ─────────────────────────────────────────────
 * Gestiona el cuerpo docente de la academia.
 * Sigue exactamente el mismo patrón que GradoServiceImpl y ClaseServiceImpl.
 */
@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorMapper instructorMapper;

    @Override
    public List<Instructor> listarInstructores() {
        return instructorRepository.findAll();
    }

    @Override
    public Optional<Instructor> instructorPorId(Integer id) {
        return instructorRepository.findById(id);
    }

    @Override
    public Instructor crearInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public void borrarInstructor(Integer id) {
        instructorRepository.deleteById(id);
    }

    // ── MÉTODOS API REST ────────────────────────────────────────────────

    /** Convierte cada Instructor a InstructorResponseDto. */
    @Override
    public List<InstructorResponseDto> listarInstructoresApi() {
        return instructorRepository.findAll()
                .stream()
                .map(instructorMapper::instructorToResponseDto)
                .toList();
    }

    /** Convierte RequestDto → entidad → guarda → ResponseDto. */
    @Override
    public InstructorResponseDto crearInstructorApi(InstructorRequestDto instructorRequestDto) {
        Instructor instructor = instructorMapper.instructorDtoToEntity(instructorRequestDto);
        instructorRepository.save(instructor);
        return instructorMapper.instructorToResponseDto(instructor);
    }

    @Override
    public InstructorResponseDto instructorPorIdApi(Integer id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado con id: " + id));
        return instructorMapper.instructorToResponseDto(instructor);
    }

    @Override
    public InstructorResponseDto actualizarInstructorApi(InstructorRequestDto instructorRequestDto, Integer id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado con id: " + id));
        instructorMapper.updateInstructorEntity(instructorRequestDto, instructor);
        instructor = instructorRepository.save(instructor);
        return instructorMapper.instructorToResponseDto(instructor);
    }

    @Override
    public void borrarInstructorApi(Integer id) {
        instructorRepository.deleteById(id);
    }
}
