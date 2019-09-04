package es.indra.censo.service;

import java.util.List;

import es.indra.censo.model.Puesto;
import es.indra.censo.model.wrapper.NoSorteableException;

public interface IPuestoService {

	public List<Puesto> findAll() throws Exception;

	// De momento no implementado en el controlador, no necesitado.
	public Puesto save(Puesto puesto) throws Exception;

	// De momento no implementado en el controlador, no necesitado.
	public void deletePuesto(Integer id) throws Exception;

	public Puesto findPuestoById(Integer id) throws Exception;
	
	public Puesto findPuestoByIdAndRegistro(Integer id, Integer idRegistro) throws Exception;

	public List<Puesto> findByPlantaOrdenados(String nombrePlanta, Integer idRegistro)
			throws NoSorteableException, Exception;

}
