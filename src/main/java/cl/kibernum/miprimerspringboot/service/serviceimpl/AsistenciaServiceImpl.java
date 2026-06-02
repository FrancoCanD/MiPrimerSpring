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

@Service
public class AsistenciaServiceImpl implements AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private AsistenciaMapper asistenciaMapper;

    @Override
    public List<Asistencia> listarTodas() { return asistenciaRepository.findAll(); }

    @Override
    public Optional<Asistencia> buscarPorId(Integer id) { return asistenciaRepository.findById(id); }

    @Override
    public Asistencia obtenerPorId(Integer id) { return asistenciaRepository.findById(id).orElse(null); }

    @Override
    public void guardar(Asistencia asistencia) { asistenciaRepository.save(asistencia); }

    @Override
    public void eliminar(Integer id) { asistenciaRepository.deleteById(id); }

    @Override
    public List<AsistenciaResponseDto> listarAsistenciasApi() {
        return asistenciaRepository.findAll()
                .stream()
                .map(asistenciaMapper::asistenciaToResponseDto)
                .toList();
    }

    @Override
    public AsistenciaResponseDto crearAsistenciaApi(AsistenciaRequestDto asistenciaRequestDto) {
        Asistencia asistencia = asistenciaMapper.asistenciaDtoToEntity(asistenciaRequestDto);
        asistenciaRepository.save(asistencia);
        return asistenciaMapper.asistenciaToResponseDto(asistencia);
    }

    @Override
    public AsistenciaResponseDto asistenciaPorIdApi(Integer id) {
        Asistencia asistencia = asistenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada con id: " + id));
        return asistenciaMapper.asistenciaToResponseDto(asistencia);
    }

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

    @Override
    public List<Asistencia> listarPorEstudianteId(Integer estudianteId) {
        return asistenciaRepository.findByEstudianteId(estudianteId);
    }

}
