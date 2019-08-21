package es.indra.censo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.indra.censo.model.Registro;

@Repository
public interface IRegistroDao extends CrudRepository<Registro, Integer> {

	public Registro findByVersion(String version);

	@Query("FROM Registro r " + "LEFT JOIN FETCH r.complejos c " + "WHERE r.idRegistro = ?1")
	public Registro findByIdWithJoinFetch(Integer id);

}
