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
 * Implementación de los servicios del grado
 */
@Service
public class GradoServiceImpl implements GradoService {
    /**
     * Inyección de dependencias por anotación Autowired
     */
    @Autowired
    private GradoRepository gradoRepository;
    @Autowired
    private GradoMapper gradoMapper;

    /**
     * Implementación de Lista todos los grados
     * @return Lista de Grados
     */
    @Override
    public List<Grado> listarGrados() {
        return gradoRepository.findAll();
    }

    /**
     * Implementación búsqueda grado por id
     * @param id grado
     * @return Grado
     */
    @Override
    public Optional<Grado> gradoPorId(Integer id) {
        return gradoRepository.findById(id);
    }

    /**
     * Implementación de Creación o guardado de grado
     * @param grado (Objeto de tipo grado)
     * @return 1
     */
    @Override
    public Grado crearGrado(Grado grado) {
        return gradoRepository.save(grado);
    }

    /**
     * Implementación Eliminar grado por ID.
     * @param id del grado a borrar
     */
    @Override
    public void borrarGrado(Integer id) {
        gradoRepository.deleteById(id);
    }

    @Override
    public List<GradoResponseDto> listarGradosApi() {
        List<GradoResponseDto> listaGradoResponseDto = new ArrayList<>();
        listaGradoResponseDto = gradoRepository.findAll()
                                    .stream()
                                    .map(gradoMapper::gradoToGradoResponseDto)
                                    .toList();
        return listaGradoResponseDto;
    }

    @Override
    public GradoResponseDto crearGradoApi(GradoRequestDto gradoRequestDto) {
        Grado grado = gradoMapper.gradoDtoToEntity(gradoRequestDto);
        gradoRepository.save(grado);
        return gradoMapper.gradoToGradoResponseDto(grado);
    }

    @Override
    public GradoResponseDto gradoPorIdApi(Integer id) {
        Grado grado = gradoRepository.findById(id).orElseThrow(()-> new RuntimeException("Grado no encontrado"));
        return gradoMapper.gradoToGradoResponseDto(grado);
    }

    @Override
    public GradoResponseDto actualizarGradoApi(GradoRequestDto gradoRequestDto, Integer id) {
        Grado grado = gradoRepository.findById(id).orElseThrow(()-> new RuntimeException("Grado no encontrado"));
        gradoMapper.updateGradoEntity(gradoRequestDto, grado);
        grado = gradoRepository.save(grado);
        return gradoMapper.gradoToGradoResponseDto(grado);
    }

    @Override
    public void borrarGradoApi(Integer id) {
        gradoRepository.deleteById(id);
    }
}
