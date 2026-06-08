package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Clase;
import cl.kibernum.miprimerspringboot.dto.request.ClaseRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.ClaseResponseDto;
import cl.kibernum.miprimerspringboot.mapper.ClaseMapper;
import cl.kibernum.miprimerspringboot.repository.ClaseRepository;
import cl.kibernum.miprimerspringboot.service.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTACIÓN DEL SERVICIO DE CLASES
 * ───────────────────────────────────────
 * Gestiona los tipos de clase impartidas en la academia (Técnica, Kata, Sparring...).
 */
@Service
public class ClaseServiceImpl implements ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private ClaseMapper claseMapper;

    @Override
    public List<Clase> listarClases() {
        return claseRepository.findAll();
    }

    @Override
    public Optional<Clase> clasePorId(Integer id) {
        return claseRepository.findById(id);
    }

    @Override
    public Clase crearClase(Clase clase) {
        return claseRepository.save(clase);
    }

    @Override
    public void borrarClase(Integer id) {
        claseRepository.deleteById(id);
    }

    // ── MÉTODOS API REST ────────────────────────────────────────────────

    /** Convierte cada Clase a ClaseResponseDto con el mapper. */
    @Override
    public List<ClaseResponseDto> listarClasesApi() {
        return claseRepository.findAll()
                .stream()
                .map(claseMapper::claseToClaseResponseDto)
                .toList();
    }

    @Override
    public ClaseResponseDto clasePorIdApi(Integer id) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con id: " + id));
        return claseMapper.claseToClaseResponseDto(clase);
    }

    @Override
    public ClaseResponseDto crearClaseApi(ClaseRequestDto claseRequestDto) {
        Clase clase = claseMapper.claseDtoToEntity(claseRequestDto);
        claseRepository.save(clase);
        return claseMapper.claseToClaseResponseDto(clase);
    }

    @Override
    public ClaseResponseDto actualizarClaseApi(ClaseRequestDto claseRequestDto, Integer id) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con id: " + id));
        claseMapper.updateClaseEntity(claseRequestDto, clase);
        clase = claseRepository.save(clase);
        return claseMapper.claseToClaseResponseDto(clase);
    }

    @Override
    public void borrarClaseApi(Integer id) {
        claseRepository.deleteById(id);
    }
}
