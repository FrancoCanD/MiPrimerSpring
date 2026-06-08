package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.dto.request.AsistenciaRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.AsistenciaResponseDto;
import cl.kibernum.miprimerspringboot.mapper.AsistenciaMapper;
import cl.kibernum.miprimerspringboot.repository.AsistenciaRepository;
import cl.kibernum.miprimerspringboot.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTACIÓN DEL SERVICIO DE ASISTENCIAS
 * ────────────────────────────────────────────
 * Contiene la lógica de negocio para registrar, consultar y eliminar asistencias.
 *
 * Usa dos colaboradores:
 *   - AsistenciaRepository: para leer/escribir en la BD
 *   - AsistenciaMapper: para convertir entre entidades y DTOs
 */
@Service
public class AsistenciaServiceImpl implements AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private AsistenciaMapper asistenciaMapper;

    /** Devuelve todas las asistencias sin transformación (usado por vistas web). */
    @Override
    public List<Asistencia> listarTodas() {
        return asistenciaRepository.findAll();
    }

    /** Busca por ID devolviendo Optional (el llamador decide qué hacer si no existe). */
    @Override
    public Optional<Asistencia> buscarPorId(Integer id) {
        return asistenciaRepository.findById(id);
    }

    /** Devuelve null si no existe (más cómodo para el formulario de edición). */
    @Override
    public Asistencia obtenerPorId(Integer id) {
        return asistenciaRepository.findById(id).orElse(null);
    }

    /** Persiste la asistencia recibida. save() hace INSERT o UPDATE según el ID. */
    @Override
    public void guardar(Asistencia asistencia) {
        asistenciaRepository.save(asistencia);
    }

    @Override
    public void eliminar(Integer id) {
        asistenciaRepository.deleteById(id);
    }

    // ── MÉTODOS API REST ────────────────────────────────────────────────

    /**
     * Lista todas las asistencias transformadas a DTO.
     * stream().map() aplica el mapper a cada elemento de la lista.
     * toList() convierte el resultado a una List inmutable.
     */
    @Override
    public List<AsistenciaResponseDto> listarAsistenciasApi() {
        return asistenciaRepository.findAll()
                .stream()
                .map(asistenciaMapper::asistenciaToResponseDto)
                .toList();
    }

    /**
     * Crea una asistencia desde un DTO de entrada:
     *   1. El mapper convierte el RequestDto en una entidad Asistencia
     *   2. Se guarda en la BD
     *   3. El mapper convierte la entidad guardada en un ResponseDto para la respuesta
     */
    @Override
    public AsistenciaResponseDto crearAsistenciaApi(AsistenciaRequestDto asistenciaRequestDto) {
        Asistencia asistencia = asistenciaMapper.asistenciaDtoToEntity(asistenciaRequestDto);
        asistenciaRepository.save(asistencia);
        return asistenciaMapper.asistenciaToResponseDto(asistencia);
    }

    /** Busca por ID, lanza excepción si no existe, y convierte a DTO. */
    @Override
    public AsistenciaResponseDto asistenciaPorIdApi(Integer id) {
        Asistencia asistencia = asistenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada con id: " + id));
        return asistenciaMapper.asistenciaToResponseDto(asistencia);
    }

    /**
     * Actualiza una asistencia existente:
     *   1. Busca la entidad en la BD (lanza excepción si no existe)
     *   2. El mapper aplica los cambios del DTO sobre la entidad existente
     *   3. Guarda y convierte a ResponseDto
     */
    @Override
    public AsistenciaResponseDto actualizarAsistenciaApi(AsistenciaRequestDto asistenciaRequestDto, Integer id) {
        Asistencia asistencia = asistenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada con id: " + id));
        asistenciaMapper.updateAsistenciaEntity(asistenciaRequestDto, asistencia);
        asistencia = asistenciaRepository.save(asistencia);
        return asistenciaMapper.asistenciaToResponseDto(asistencia);
    }

    @Override
    public void borrarAsistenciaApi(Integer id) {
        asistenciaRepository.deleteById(id);
    }
}
