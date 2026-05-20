package cl.kibernum.miprimerspringboot.service.serviceimpl;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
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
}
