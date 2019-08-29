package es.indra.censo.service;

import java.util.List;

import org.springframework.dao.InvalidDataAccessResourceUsageException;

import es.indra.censo.model.Rol;
import es.indra.censo.model.Usuario;

public interface IRolService {
	
	public List<Rol> encontrarRolesQueNoTiene(Usuario user) throws InvalidDataAccessResourceUsageException, Exception;

}
