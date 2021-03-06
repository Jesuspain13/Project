package es.indra.censo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.controllers.auth.UsuarioDTO;
import es.indra.censo.dao.IRolDao;
import es.indra.censo.dao.IUsuarioDao;
import es.indra.censo.model.Rol;
import es.indra.censo.model.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	private Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private IUsuarioDao usuarioDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private IRolDao rolDao;

	@Override
	public List<Usuario> findUsuarioByName(String username) throws Exception {

		try {
			List<Usuario> users = usuarioDao.findFullUsuarioByName(username);
			if (users == null || users.size() < 1) {
				return new ArrayList<Usuario>();
			}
			for (Usuario u : users) {
				u.setPassword(null);
			}
			return users;

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);

		}

	}

	/**
	 * establece el atributo enabled a false
	 */
	@Override
	@Transactional
	public void deleteUsuarioById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		try {
			Usuario user = usuarioDao.findById(id).get();
			user.setEnabled(false);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}

	}

	public Usuario findUsuarioById(Integer id) throws Exception {
		try {
			return usuarioDao.findById(id).get();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}


	@Override
	@Transactional
	public void modificarUsuario(int id, UsuarioDTO usuarioConDatosNuevos) throws Exception {
		try {
			Usuario usuarioAModificar = usuarioDao.findById(id).get();
			usuarioAModificar.setUsername(usuarioConDatosNuevos.getUsername());
			String contraseñaFormularioDesencriptada = usuarioConDatosNuevos.getPasswordDecoded();
			if (contraseñaFormularioDesencriptada == null ||
					contraseñaFormularioDesencriptada.isEmpty()) {
				contraseñaFormularioDesencriptada = usuarioConDatosNuevos.decodePasswordEncoded();
			}
			String passBCryptEncoded = this.passwordEncoder.encode(contraseñaFormularioDesencriptada);
			usuarioAModificar.setPassword(passBCryptEncoded);

			usuarioDao.save(usuarioAModificar);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	@Transactional
	public void guardarUsuario(UsuarioDTO usuarioConDatosNuevos) throws Exception {
		try {

			Usuario usuario = new Usuario();
			usuario.setUsername(usuarioConDatosNuevos.getUsername());
			String pass1 = usuarioConDatosNuevos.getPasswordEncoded();
			String pass = usuarioConDatosNuevos.decodePasswordEncoded();
			System.out.println("contraseña codificada: " + pass1);
			System.out.println("contraseña: " + pass);
			String passBCryptEncoded = this.passwordEncoder.encode(pass);
			usuario.setPassword(passBCryptEncoded);
			usuario.setEnabled(true);
			this.comprobarPermiso(usuarioConDatosNuevos.getRol(), usuario);

			usuarioDao.save(usuario);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	private void comprobarPermiso(String rolSeleccionado, Usuario usuario) {
		List<Rol> roles = (List<Rol>) rolDao.findAll();
		if (rolSeleccionado.contains("ADMIN")) {
			usuario.setRoles(roles);
		} else if (rolSeleccionado.contains("VISUALIZAR")) {
			Rol rol = rolDao.findByAuthority(rolSeleccionado);
			usuario.addRol(rol);
		}
	}

	@Override
	@Transactional
	public void eliminarRolUsuario(Integer rolId, Integer usuarioId) throws Exception {
		// TODO Auto-generated method stub
		try {
			Usuario user = usuarioDao.findById(usuarioId).get();
			Rol rolABorrar = rolDao.findById(rolId).get();
			if (rolABorrar == null || user == null) {

				throw new Exception("NOT FOUND");
			}
			user.deleteRol(rolABorrar);

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}
	
	@Override
	@Transactional
	public void añadirRolUsuario(Integer rolId, Integer usuarioId) throws Exception {
		// TODO Auto-generated method stub
		try {
			Usuario user = usuarioDao.findById(usuarioId).get();
			Rol rolAñadir= rolDao.findById(rolId).get();
			if (rolAñadir == null || user == null) {
				
				throw new Exception("NOT FOUND");
			}
			user.addRol(rolAñadir);
			
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}


	@Override
	@Transactional
	public void modificarEstado(int id) throws Exception {

		try {
			Usuario user = usuarioDao.findById(id).get();

			boolean enabled = user.getEnabled();

			if (enabled) {
				user.setEnabled(false);
			} else {
				user.setEnabled(true);
			}

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

}
