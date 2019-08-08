package es.indra.censo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;

public interface IPuestoDao extends CrudRepository <Puesto, Integer>{
	
	public List<Puesto> findByPlanta(Planta p);

}
