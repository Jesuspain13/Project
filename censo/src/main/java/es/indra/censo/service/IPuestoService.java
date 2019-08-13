package es.indra.censo.service;

import java.util.List;

import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.Registro;
import es.indra.censo.model.wrapper.NoSorteableException;

public interface IPuestoService {

	public List<Puesto> findAll();
	
	//De momento no implementado en el controlador, no necesitado.
	public void save(Puesto puesto);

	//De momento no implementado en el controlador, no necesitado.
	public void deletePuesto(Integer id);

	public Puesto findPuestoById(Integer id);
	
	public List<Puesto> findByPlantaOrdenados(Planta p)  throws NoSorteableException, Exception;
	
	public List<Puesto> findByPlantaOrdenados(Integer nombrePlanta, Integer idRegistro)  throws NoSorteableException, Exception;

}
