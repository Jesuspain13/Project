package es.indra.censo.service;

import java.util.List;

import es.indra.censo.model.Planta;

public interface IPlantaService {
	
	public List <Planta> findAll() throws Exception;
	
	//De momento no implementado en el controlador, no necesitado.
	public void save (Planta planta) throws Exception;
	
	//De momento no implementado en el controlador, no necesitado.
	public void deletePlanta(Integer id) throws Exception;
	
	public Planta findPlantaById (Integer id) throws Exception;
	
	public Planta findPlantaByNombrePlanta(Integer nombrePlanta) throws Exception;
	
	public Planta findPlantaByIdPlantaAndRegistro(Integer idPlanta, int idRegistro) throws Exception;
	
	public Planta findPlantaByNombrePlantaAndRegistro(Integer nombrePlanta, Integer idRegistro) throws Exception;

}
