package es.indra.censo.service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;


import es.indra.censo.model.Empleado;

public interface IEmpleadoService {

	public List<Empleado> findAll() throws Exception;
	
	public Empleado findEmpleadoById(Integer id) throws Exception;
	
	public Empleado findEmpleadoByIdPuesto(Integer id) throws Exception;
	

	public Page<Empleado> findEmpleadoByNombreYApellidos(Integer idRegistro, String nombre, String apellidos, int pageNumber) throws Exception;

	public Empleado findEmpleadoByIdWithPuestoAndPlanta(Integer idEmpleado, Integer idRegistro) throws Exception;
	

	public void crearEmpleado (Empleado empleado)throws Exception;
}
