package cl.kibernum.miprimerspringboot.service;

import cl.kibernum.miprimerspringboot.bl.entity.Grado;

import java.util.List;
import java.util.Optional;

/**
 * Interface del servicio de grado
 * contiene los metodos abstractos del Grado
 */
public interface GradoService {
    /**
     * Lista todos los grados existentes en la BD
     * @return Lista de Grados
     */
    List<Grado> listarGrados();

    /**
     * Buscar grado por ID
     * @param id
     * @return Grado según id
     */
    Optional<Grado> gradoPorId(Integer id);

    /**
     * Crear un nuevo grado
     * @param grado (Objeto de tipo grado)
     * @return 1
     */
    Grado crearGrado(Grado grado);

    /**
     * Borra un grado según id
     * @param id del grado a borrar
     */
   void borrarGrado(Integer id);
}
