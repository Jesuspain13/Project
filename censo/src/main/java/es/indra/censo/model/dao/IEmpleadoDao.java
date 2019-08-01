package es.indra.censo.model.dao;

import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Empleado;

public interface IEmpleadoDao extends CrudRepository<Empleado, Integer> {

}
