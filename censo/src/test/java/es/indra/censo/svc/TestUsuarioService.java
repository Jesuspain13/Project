package es.indra.censo.svc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.controllers.auth.UsuarioDTO;
import es.indra.censo.dao.IRolDao;
import es.indra.censo.dao.IUsuarioDao;
import es.indra.censo.model.Rol;
import es.indra.censo.model.Usuario;
import es.indra.censo.service.IUsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUsuarioService {

	private Logger log = LoggerFactory.getLogger(TestUsuarioService.class);

	@Autowired
	private IUsuarioService uSvc;

	@Autowired
	private IUsuarioDao uDao;

	private Usuario userToTest;

	@Autowired
	private IRolDao rDao;

	private Rol rolToTest;

	@Before
	public void before() {
		List<Usuario> usuarios = (List<Usuario>) uDao.findAll();
		this.userToTest = usuarios.get(0);
		List<Rol> roles = (List<Rol>) rDao.findAll();
		this.rolToTest = roles.get(0);

	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void testFindUsuarioByName() {
		try {
			List<Usuario> usuariosEncontrados = uSvc.findUsuarioByName(userToTest.getUsername());
			assertNotNull(usuariosEncontrados);
			assertTrue(usuariosEncontrados.size() > 0);
		} catch (Exception ex) {
			log.error(ex.getStackTrace().toString());
		}
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void testDeleteUsuarioById() {
		try {
			uSvc.deleteUsuarioById(userToTest.getId());
			Usuario userDeleted = uDao.findById(userToTest.getId()).get();
			assertFalse(userDeleted.getEnabled());

		} catch (Exception ex) {
			log.error(ex.getStackTrace().toString());
		}
	}

	@Test

	@Rollback(value = true)
	public void testiModificarUsuario() {
		try {
			UsuarioDTO usuarioConDatos = new UsuarioDTO();
			usuarioConDatos.setUsername("Pepe");
			usuarioConDatos.setPasswordDecoded("123");
			uSvc.modificarUsuario(userToTest.getId(), usuarioConDatos);
			Usuario usuarioModificadoEnBD = uSvc.findUsuarioById(userToTest.getId());

			boolean result = usuarioModificadoEnBD.equals(userToTest);

			assertFalse(result);

		} catch (Exception ex) {
			log.error(ex.getStackTrace().toString());
		}
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void testiGuardarUsuario() {
		try {
			UsuarioDTO usuarioConDatos = new UsuarioDTO();
			usuarioConDatos.setUsername("prueba test");
			usuarioConDatos.setPasswordEncoded("12345");
			usuarioConDatos.setRol(userToTest.getRoles().get(0).getAuthority());
			uSvc.guardarUsuario(usuarioConDatos);
			List<Usuario> usuarioGuardado = uSvc.findUsuarioByName(usuarioConDatos.getUsername());
			boolean encontrado = false;
			int contador = 0;
			Usuario iter;
			while (!encontrado && contador < usuarioGuardado.size()) {
				iter = usuarioGuardado.get(contador);
				if (iter.getUsername().contentEquals(usuarioConDatos.getUsername())) {
					encontrado = true;
				}
				contador++;
			}
			assertTrue(encontrado);

		} catch (Exception ex) {
			log.error(ex.getStackTrace().toString());
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	@Transactional
	@Rollback(value = true)
	public void testEliminarRolUsuario() {
		try {
			uSvc.eliminarRolUsuario(userToTest.getRoles().get(0).getRolId(), userToTest.getId());
			Usuario userFound = uSvc.findUsuarioById(userToTest.getId());
			assertFalse(userToTest.getRoles().contains(userFound.getRoles()));
		} catch (Exception ex) {
			log.error(ex.getStackTrace().toString());
		}

	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	@Transactional
	@Rollback(value = true)
	public void testAñadirRolUsuario() {
		try {
			uSvc.añadirRolUsuario(userToTest.getRoles().get(0).getRolId(), userToTest.getId());
			Usuario userFound = uSvc.findUsuarioById(userToTest.getId());
			assertFalse(userToTest.getRoles().contains(userFound.getRoles()));
		} catch (Exception ex) {
			log.error(ex.getStackTrace().toString());
		}

	}

	@Test
	@Rollback(value = true)
	public void testModificarEstado() {
		try {

			uSvc.modificarEstado(userToTest.getId());
			Usuario userFound = uSvc.findUsuarioById(userToTest.getId());

			boolean resultado = userFound.getEnabled() != userToTest.getEnabled();

			assertTrue(resultado);
		} catch (Exception ex) {
			log.error(ex.getStackTrace().toString());
		}

	}

}
