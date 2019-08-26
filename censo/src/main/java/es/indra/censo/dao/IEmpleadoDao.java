package es.indra.censo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Empleado;

public interface IEmpleadoDao extends CrudRepository<Empleado, Integer> {
	
	@Query("SELECT e FROM Empleado e WHERE e.puesto.idPuestoAuto = ?1")
	public Empleado findEmpleadoByIdPuesto(Integer idPuesto) throws Exception;

}
