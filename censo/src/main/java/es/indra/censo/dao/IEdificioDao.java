package es.indra.censo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Edificio;

public interface IEdificioDao extends CrudRepository <Edificio, Integer> {
	
	@Query("SELECT e FROM Edificio e WHERE e.idEdificio=?1 AND e.registro.idRegistro=?2")
	public Edificio findByIdAndRegistro(Integer idEdificio, Integer r);

}
