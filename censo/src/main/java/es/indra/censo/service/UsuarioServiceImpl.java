package es.indra.censo.service;

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

//	@Override
//	public List<Usuario> findUsuarioByName(String username) throws Exception {
//		
//		try {
//			List user = usuarioDao.findUsuarioByName(username);
//			Iterator userIt = user.iterator();
//			List<Usuario> usuarios = new ArrayList<Usuario>();
//			
//			while(userIt.hasNext()) {
//				List<Object> usuarioSinFormar = (List<Object>) userIt.next();
//				Usuario usuarioFormado = new Usuario();
//				usuarioFormado.setId((Integer)usuarioSinFormar.get(0));
//				usuarioFormado.setUsername(usuarioSinFormar.get(1).toString());
//				usuarioFormado.setEnabled((boolean) usuarioSinFormar.get(2));
//				usuarios.add(usuarioFormado);
//			}
//			return usuarios;
//			
//		}catch (Exception ex){
//			
//			log.error(ex.getMessage());
//			throw new Exception(ex);
//			
//		}
//		
//	}

	@Override
	public List<Usuario> findUsuarioByName(String username) throws Exception {

		try {
			List<Usuario> users = usuarioDao.findFullUsuarioByName(username);

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

	public void updateUsuario(Usuario usuario) throws Exception {
		try {
			usuarioDao.save(usuario);
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
			String passBCryptEncoded = this.passwordEncoder.encode(usuarioConDatosNuevos.getPasswordDecoded());
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
			String pass = usuarioConDatosNuevos.getPasswordDecoded();
			System.out.println("contraseña codificada: " + pass1);
			System.out.println("contraseña: " + pass);
			String passBCryptEncoded = this.passwordEncoder
					.encode(pass);
			usuario.setPassword(passBCryptEncoded);
			usuario.setEnabled(true);
			this.comprobarPermiso(usuarioConDatosNuevos.getRol(), usuario);

			usuarioDao.save(usuario);
		} catch (

		Exception ex) {
			log.error(ex.getMessage());
			throw new Exception(ex);
		}
	}
	
	public void comprobarPermiso(String rolSeleccionado, Usuario usuario) {
		List<Rol> roles = (List<Rol>) rolDao.findAll();
		if (rolSeleccionado.contains("ADMIN")) {
			usuario.setRoles(roles);
		} else if (rolSeleccionado.contains("VISUALIZAR")) {
			Rol rol = rolDao.findByAuthority(rolSeleccionado);
			usuario.addRol(rol);
		}
	}

}
