package es.indra.censo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Registro;
import es.indra.censo.model.Ue;

public interface IUeDao extends CrudRepository <Ue, Integer>{
	
	@Query("SELECT u FROM Ue u WHERE u.idUe=?1 AND u.registro=?2")
	public Ue findByIdUe(String idUe, Registro registro);

}
