package es.indra.censo.service;

import java.util.List;

import es.indra.censo.model.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> findUsuarioByName (String username) throws Exception;
	
	public void deleteUsuarioById(Integer id) throws Exception;

}
