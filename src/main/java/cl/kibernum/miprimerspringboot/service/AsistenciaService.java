package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Asistencia;
import cl.kibernum.miprimerspringboot.dto.request.AsistenciaRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.AsistenciaResponseDto;

import java.util.List;
import java.util.Optional;

public interface AsistenciaService {
    List<Asistencia> listarTodas();
    Optional<Asistencia> buscarPorId(Integer id);
    Asistencia obtenerPorId(Integer id);
    void guardar(Asistencia asistencia);
    void eliminar(Integer id);

    // Métodos API REST con DTOs
    List<AsistenciaResponseDto> listarAsistenciasApi();
    AsistenciaResponseDto crearAsistenciaApi(AsistenciaRequestDto asistenciaRequestDto);
    AsistenciaResponseDto asistenciaPorIdApi(Integer id);
    AsistenciaResponseDto actualizarAsistenciaApi(AsistenciaRequestDto asistenciaRequestDto, Integer id);
    void borrarAsistenciaApi(Integer id);
}
