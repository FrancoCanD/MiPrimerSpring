package cl.kibernum.miprimerspringboot.mapper;

import cl.kibernum.miprimerspringboot.bl.entity.Estudiante;
import cl.kibernum.miprimerspringboot.dto.request.EstudianteRequestDto;
import cl.kibernum.miprimerspringboot.dto.response.EstudianteResponseDto;
import org.springframework.stereotype.Component;

@Component
public class EstudianteMapper {

    public Estudiante estudianteDtoToEntity(EstudianteRequestDto estudianteDto) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombres(estudianteDto.getNombres());
        estudiante.setApellido1(estudianteDto.getApellido1());
        estudiante.setApellido2(estudianteDto.getApellido2());
        estudiante.setFechaNac(estudianteDto.getFechaNac());
        estudiante.setRut(estudianteDto.getRut());
        estudiante.setGrado(estudianteDto.getGradoRequestDto());
        estudiante.setActivo(estudianteDto.isActivo());
        return estudiante;
    }

    public EstudianteResponseDto estudianteToEstudianteResponseDto(Estudiante estudiante) {
        EstudianteResponseDto estudianteResponseDto = new EstudianteResponseDto();
        estudianteResponseDto.setId(estudiante.getId());
        estudianteResponseDto.setNombres(estudiante.getNombres());
        estudianteResponseDto.setApellido1(estudiante.getApellido1());
        estudianteResponseDto.setApellido2(estudiante.getApellido2());
        estudianteResponseDto.setFechaNac(estudiante.getFechaNac());
        estudianteResponseDto.setRut(estudiante.getRut());
        estudianteResponseDto.setGradoResponseDto(estudiante.getGrado());
        estudianteResponseDto.setFechaAscenso(estudiante.getFechaAscenso());
        estudianteResponseDto.setActivo(estudiante.isActivo());
        return estudianteResponseDto;
    }

    public void updateEstudianteEntity(EstudianteRequestDto estudianteDto, Estudiante estudiante) {
        estudiante.setNombres(estudianteDto.getNombres());
        estudiante.setApellido1(estudianteDto.getApellido1());
        estudiante.setApellido2(estudianteDto.getApellido2());
        estudiante.setFechaNac(estudianteDto.getFechaNac());
        estudiante.setRut(estudianteDto.getRut());
        estudiante.setGrado(estudianteDto.getGradoRequestDto());
        estudiante.setFechaAscenso(estudianteDto.getFechaAscenso());
        estudiante.setActivo(estudianteDto.isActivo());
    }
}
