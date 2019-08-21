package es.indra.censo.service;

import java.util.List;

import es.indra.censo.model.Empleado;

public interface IEmpleadoService {

	public List<Empleado> findAll() throws Exception;

	public Empleado findEmpleadoById(Integer id) throws Exception;
}
