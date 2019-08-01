package es.indra.censo.model.service;

import java.util.List;

import es.indra.censo.model.Planta;

public interface IPlantaService {
	
	public List <Planta> findAll();
	
	//De momento no implementado en el controlador, no necesitado.
	public void save (Planta planta);
	
	//De momento no implementado en el controlador, no necesitado.
	public void deletePlanta(Integer id);
	
	public Planta findPlantaById (Integer id);

}
