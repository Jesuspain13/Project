package es.indra.censo.service;

import java.util.List;

import es.indra.censo.model.Registro;

public interface IRegistroService {
	
	public List<Registro> findAll();
	
	public Registro save (Registro registro);
	
	public Registro findRegistroById (Integer id);
	
	public Registro findRegistroByVersion(String version);

}
