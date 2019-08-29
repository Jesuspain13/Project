package es.indra.censo.service;

import java.util.List;

import es.indra.censo.controllers.auth.UsuarioDTO;
import es.indra.censo.model.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> findUsuarioByName (String username) throws Exception;
	
	public void deleteUsuarioById(Integer id) throws Exception;
	
	public Usuario findUsuarioById(Integer id) throws Exception;
	
	public void updateUsuario(Usuario usuario) throws Exception;
	
	/**
	 * asigna los datos recogidos en el formulario al usuario que coincida con el id
	 * @param id id del usuario a modificar
	 * @param usuarioConDatosNuevos datos del formulario
	 * @throws Exception
	 */
	public void modificarUsuario(int id, UsuarioDTO usuarioConDatosNuevos) throws Exception;
	
	/**
	 * Crea ujn usuario nuevo a partir de losd atos recogidos desde el formulario
	 * @param usuarioConDatosNuevos datos del formulario
	 * @throws Exception
	 */
	public void guardarUsuario(UsuarioDTO usuarioConDatosNuevos) throws Exception;
	
	public void eliminarRolUsuario(Integer rolId, Integer usuarioId) throws Exception;
	
	public void añadirRolUsuario(Integer rolId, Integer usuarioId) throws Exception;


}
