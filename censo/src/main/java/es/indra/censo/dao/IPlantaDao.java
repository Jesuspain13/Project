package es.indra.censo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Planta;

public interface IPlantaDao extends CrudRepository<Planta, Integer> {

	public Planta findByNombrePlanta(String nombrePlanta);

	@Query("SELECT p FROM Planta p " + "LEFT JOIN FETCH p.edificio e " + "LEFT JOIN FETCH e.plantas pl "
			+ "WHERE p.id=?1 AND p.registro.idRegistro=?2")
	public Planta findByIdPlantaAndRegistro(Integer idPlanta, Integer r);

	@Query("SELECT p FROM Planta p WHERE p.nombrePlanta=?1 AND p.registro.idRegistro=?2")
	public Planta findByNombrePlantaAndRegistro(String nombrePlanta, Integer idRegistro);
	
	@Query("SELECT p FROM Planta p "
			+ "LEFT JOIN FETCH p.edificio e"
			+ "LEFT JOIN FETCH p.puestos pu WHERE p.edificio.id=?1 AND p.registro.idRegistro=?2")
	public List<Planta> findByIdEdificioAndRegistro(Integer idEdificio, Integer idRegistro);

}
