package es.indra.censo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.Registro;

public interface IPuestoDao extends CrudRepository<Puesto, Integer> {

	public List<Puesto> findByPlanta(Planta p);

	@Query("SELECT p FROM Puesto p " + "LEFT JOIN FETCH p.empleado e " + "LEFT JOIN FETCH e.ue ue "
			+ "LEFT JOIN FETCH p.planta planta LEFT JOIN FETCH planta.edificio ed " 
			+ "LEFT JOIN FETCH ue.ueRepercutible ueRep "
			+ "WHERE p.planta.nombrePlanta=?1 " + "AND p.registro.idRegistro=?2")
	public List<Puesto> findByPlantaAndRegistro(String p, Integer r);

}
