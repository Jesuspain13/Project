package es.indra.censo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Complejo;
import es.indra.censo.model.Registro;

public interface IComplejoDao extends CrudRepository <Complejo, Integer> {
	
	@Query("SELECT c FROM Complejo c WHERE c.id=?1 AND c.registro=?2")
	public Complejo findByIdAndRegistro(Integer id, Registro r);

}
