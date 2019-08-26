package es.indra.censo.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import es.indra.censo.model.Empleado;

public interface IEmpleadoService {

	public List<Empleado> findAll() throws Exception;
	
	public Empleado findEmpleadoById(Integer id) throws Exception;
	
	public Empleado findEmpleadoByIdPuesto(Integer id) throws Exception;
}
