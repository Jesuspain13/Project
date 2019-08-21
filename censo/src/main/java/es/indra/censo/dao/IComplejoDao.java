package es.indra.censo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Complejo;

public interface IComplejoDao extends CrudRepository<Complejo, Integer> {

	@Query("SELECT c FROM Complejo c WHERE c.id = ?1 AND c.registro.idRegistro = ?2")
	public Complejo findByIdAndRegistro(Integer id, Integer r);

	@Query("SELECT c FROM Complejo c " + "LEFT JOIN FETCH c.edificios e " + "LEFT JOIN FETCH c.registro r "
			+ "WHERE c.id = ?1 " + "AND c.registro.idRegistro = ?2")
	public Complejo findByIdAndRegistroWithJoinFetch(Integer idComplejo, Integer idRegistro);

}
