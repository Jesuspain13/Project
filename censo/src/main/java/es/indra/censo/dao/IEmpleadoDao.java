package es.indra.censo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import es.indra.censo.model.Empleado;

public interface IEmpleadoDao extends PagingAndSortingRepository<Empleado, Integer> {
	
	@Query("SELECT e FROM Empleado e WHERE e.puesto.idPuestoAuto = ?1")
	public Empleado findEmpleadoByIdPuesto(Integer idPuesto) throws Exception;
	
	@Query(value= "SELECT e FROM Empleado e WHERE"
			+ " e.registro.idRegistro = :idRegistro AND"
			+ " e.nombre LIKE CONCAT('%',:name,'%') AND "
			+ "e.apellido LIKE  CONCAT('%',:surname,'%')",
			countQuery = "SELECT COUNT(*) FROM Empleado e WHERE e.registro.idRegistro = :idRegistro AND e.nombre LIKE CONCAT('%',:name,'%') AND "
					+ "e.apellido LIKE  CONCAT('%',:surname,'%')")
	public Page<Empleado> findEmpleadoByNameAndSurname(@Param("idRegistro") Integer idRegistro, @Param("name") String name,
			@Param("surname") String surname, Pageable pageable);
	
	@Query("SELECT e FROM Empleado e "
			+ "LEFT JOIN FETCH e.puesto p "
			+ "LEFT JOIN FETCH p.planta planta "
			+ "LEFT JOIN FETCH planta.edificio edif "
			+ "LEFT JOIN FETCH e.registro r "
			+ "WHERE e.idEmpleadoAuto = ?1 "
			+ "AND e.registro.idRegistro = ?2")
	public Empleado findByIdWithPuestoAndPlanta(Integer idEmpleado, Integer idRegistro);

}
