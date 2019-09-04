package es.indra.censo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Ue;

public interface IUeDao extends CrudRepository<Ue, Integer> {

	@Query("SELECT u FROM Ue u WHERE u.id=?1")
	public Ue findByIdUe(Integer idUe);
	
	@Query("SELECT u FROM Ue u WHERE u.registro.idRegistro=?1")
	public List<Ue> findAllByIdRegistro(Integer idRegistro);
	
}
