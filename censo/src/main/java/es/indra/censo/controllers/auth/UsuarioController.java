package es.indra.censo.controllers.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.dao.IRolDao;
import es.indra.censo.model.Rol;
import es.indra.censo.model.Usuario;
import es.indra.censo.service.IRolService;
import es.indra.censo.service.IUsuarioService;

@Controller
@RequestMapping("/usuarios")
@Secured({ "ROLE_ADMIN" })
public class UsuarioController {

	private Logger log = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private IRolDao rolDao;
	
	@Autowired
	private IRolService rolSvc;


	/**
	 * encuentra todos los usuarios que contengan el nombre
	 * @param nombre
	 * @param flash
	 * @return
	 */
	@GetMapping("/buscar")
	@ResponseBody
	public List<Usuario> listarUsuarioPorNombre(@RequestParam(name = "nombre") String nombre,
			RedirectAttributes flash, Locale locale) {

		String name = nombre;

		List<Usuario> usuarios;
		try {
			usuarios = usuarioService.findUsuarioByName(name);
			return usuarios;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));

			return null;
		}
	}

	/**
	 * Eliminar usuario por el id 
	 * @param id
	 * @param flash
	 * @return
	 */
	@GetMapping("/eliminar/{id}")
	public String borrarUsuarioPorId(@PathVariable(name = "id") Integer id, RedirectAttributes flash, Locale locale) {
		try {
			usuarioService.deleteUsuarioById(id);
			String msg = msgSource.getMessage("text.usuario.delete", null, locale);
			flash.addFlashAttribute("success", String.format(msg));
			return "redirect:/usuarios/registro";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));

			return "redirect:/usuario/registro";
		}

	}

	/**
	 * Busca el usuario que se quiere modificar en la BD y lo manda al formulario
	 * @param id
	 * @param flash
	 * @param model
	 * @return
	 */
	@GetMapping("/modificar/{id}")
	public String modificarUsuario(@PathVariable(name = "id") Integer id, RedirectAttributes flash,
			Model model, Locale locale) {
		try {
			Usuario usuario = usuarioService.findUsuarioById(id);
			// Si no encuentra usuario
			if (usuario == null) {
				String msg = msgSource.getMessage("text.error.encontrar.usuario", null, locale);
				flash.addFlashAttribute("error", msg);
				return "redirect:/usuarios/registro";
			}
			UsuarioDTO usuarioDto = new UsuarioDTO();
			usuarioDto.setUsername(usuario.getUsername());
			usuarioDto.setIdUser(usuario.getId());
			//buscar roles que no tiene el usuario a modificar
			List<Rol> rolesQueNoTiene = rolSvc.encontrarRolesQueNoTiene(usuario);
			model.addAttribute("usuario", usuarioDto);


			model.addAttribute("roles", usuario.getRoles());
			model.addAttribute("rolesNoAsignados", rolesQueNoTiene);


			return "register";
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));

			return "redirect:/usuarios/registro";
		}

	}

	/**
	 * Recibe los nuevos datos del formulario y los asigna al usuario que coincida con el id
	 * @param id
	 * @param user
	 * @param flash
	 * @param model
	 * @return
	 */
	@PostMapping("/modificar/{id}")
	public String modificarUsuario(@PathVariable(name = "id") Integer id,
			UsuarioDTO user, RedirectAttributes flash, Model model) {
		try {
			usuarioService.modificarUsuario(id, user);
			flash.addFlashAttribute("success", "Se ha modificado el usuario");
			return "redirect:/usuarios/registro";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, new Locale("es", "ES"));
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));

			return "redirect:/usuarios/registro";
		}

	}

	/**
	 * Crea un usuario para el mapeo con el formulario
	 * 
	 * @param model
	 * @param flash
	 * @param locale
	 * @return
	 */
	@GetMapping("/registro")
	public String registrarUsuario(Model model, RedirectAttributes flash, Locale locale) {
		try {
			UsuarioDTO usuario = new UsuarioDTO();
			List<Rol> roles = (List<Rol>) rolDao.findAll();
			model.addAttribute("usuarioDto", usuario);
			model.addAttribute("roles", roles);
			return "register";
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}

	/**
	 * recibe los datos del formulario, los asigna a un Usuario y los guarda en la
	 * BD
	 * 
	 * @param usuarioDto datos del formulario
	 * @param model
	 * @param flash
	 * @param locale
	 * @return
	 */
	@PostMapping("/registro")
	public String registrarUsuario(UsuarioDTO usuarioDto, Model model, RedirectAttributes flash, Locale locale) {
		try {
			usuarioService.guardarUsuario(usuarioDto);
			return "home";
		} catch (Exception ex) {
			
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}
	
	@GetMapping(value="/borrar/rol")
	@ResponseBody
	public Map<String, Object> eliminarRol(@RequestParam(name="rolId") Integer rolId,
			@RequestParam(name="usuarioId") Integer usuarioId){
			 
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			usuarioService.eliminarRolUsuario(rolId, usuarioId);
			result.put("borrado", true);
			return result;
		}catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			Map<String, Object> badResult = new HashMap<String, Object>();
			badResult.put("borrado", false);
			return badResult;
		}
	}
	

	@GetMapping(value="/add/rol")
	@ResponseBody
	public Map<String, Object> añadirRol(@RequestParam(name="rolId") Integer rolId,
			@RequestParam(name="usuarioId") Integer usuarioId){
			 
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			usuarioService.añadirRolUsuario(rolId, usuarioId);
			result.put("añadido", true);
			return result;
		}catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			Map<String, Object> badResult = new HashMap<String, Object>();
			badResult.put("añadido", false);
			return badResult;
		}
	}

	@GetMapping("/modificarEstado")
	@ResponseBody
	public Map<String, Object> modificarEstado(@RequestParam(name="idUsuario")Integer idUsuario) {
		
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			usuarioService.modificarEstado(idUsuario);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			return null;
			
		}
				
	}

}
