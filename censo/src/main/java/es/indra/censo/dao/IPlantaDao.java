package es.indra.censo.dao;

import org.springframework.data.repository.CrudRepository;

import es.indra.censo.model.Planta;

public interface IPlantaDao extends CrudRepository <Planta, Integer> {

	public Planta findByNombrePlanta(String nombrePlanta);
	
}
