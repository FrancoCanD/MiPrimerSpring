package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import java.util.List;
import java.util.Optional;

public interface AsistenciaService {
    List<Asistencia> listarTodas();
    Optional<Asistencia> buscarPorId(Integer id);
    Asistencia obtenerPorId(Integer id);
    void guardar(Asistencia asistencia);
    void eliminar(Integer id);
}
