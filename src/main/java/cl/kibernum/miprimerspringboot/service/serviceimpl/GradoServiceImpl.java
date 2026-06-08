package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Grado;
import cl.kibernum.miprimerspringboot.dto.request.GradoRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.GradoResponseDto;
import cl.kibernum.miprimerspringboot.mapper.GradoMapper;
import cl.kibernum.miprimerspringboot.repository.GradoRepository;
import cl.kibernum.miprimerspringboot.service.GradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTACIÓN DEL SERVICIO DE GRADOS
 * ───────────────────────────────────────
 * Lógica de negocio para gestionar la escala de cinturones/grados.
 */
@Service
public class GradoServiceImpl implements GradoService {

    @Autowired
    private GradoRepository gradoRepository;

    @Autowired
    private GradoMapper gradoMapper;

    @Override
    public List<Grado> listarGrados() {
        return gradoRepository.findAll();
    }

    @Override
    public Optional<Grado> gradoPorId(Integer id) {
        return gradoRepository.findById(id);
    }

    @Override
    public Grado crearGrado(Grado grado) {
        return gradoRepository.save(grado);
    }

    @Override
    public void borrarGrado(Integer id) {
        gradoRepository.deleteById(id);
    }

    // ── MÉTODOS API REST ────────────────────────────────────────────────

    /**
     * Convierte cada Grado a GradoResponseDto usando el mapper.
     * Patrón: entidad → mapper → DTO
     * El DTO solo expone los campos necesarios para la API, sin información interna.
     */
    @Override
    public List<GradoResponseDto> listarGradosApi() {
        return gradoRepository.findAll()
                .stream()
                .map(gradoMapper::gradoToGradoResponseDto)
                .toList();
    }

    /**
     * Convierte el RequestDto a entidad → guarda → convierte a ResponseDto.
     * El RequestDto llega desde el body de la petición HTTP (JSON).
     */
    @Override
    public GradoResponseDto crearGradoApi(GradoRequestDto gradoRequestDto) {
        Grado grado = gradoMapper.gradoDtoToEntity(gradoRequestDto);
        gradoRepository.save(grado);
        return gradoMapper.gradoToGradoResponseDto(grado);
    }

    @Override
    public GradoResponseDto gradoPorIdApi(Integer id) {
        Grado grado = gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));
        return gradoMapper.gradoToGradoResponseDto(grado);
    }

    /**
     * Actualiza un grado existente:
     *   1. Busca la entidad por ID (lanza excepción si no existe)
     *   2. El mapper aplica los cambios del DTO a la entidad existente
     *   3. Guarda (UPDATE) y retorna como DTO
     */
    @Override
    public GradoResponseDto actualizarGradoApi(GradoRequestDto gradoRequestDto, Integer id) {
        Grado grado = gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));
        gradoMapper.updateGradoEntity(gradoRequestDto, grado);
        grado = gradoRepository.save(grado);
        return gradoMapper.gradoToGradoResponseDto(grado);
    }

    @Override
    public void borrarGradoApi(Integer id) {
        gradoRepository.deleteById(id);
    }
}
