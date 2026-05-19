package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;

import java.util.List;
import java.util.Optional;

/**
 * Interface del Servicio de Asistencia
 * Contiene los métoodos abstarctos de la Asistencia
 */
public interface AsistenciaService {
    /**
     *
     */
    List<Asistencia> listarTodas();
    /**
     *
     */
    Optional<Asistencia> buscarPorId(Integer id);
    /**
     *
     */
    Asistencia obtenerPorId(Integer id);
    /**
     *
     */
    void eliminar(Integer id);

    void guardar(Asistencia asistencia);
}
