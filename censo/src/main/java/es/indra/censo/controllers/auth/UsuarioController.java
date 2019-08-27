package es.indra.censo.controllers.auth;

import java.util.List;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.dao.IRolDao;
import es.indra.censo.model.Rol;
import es.indra.censo.model.Usuario;
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

	/**
	 * encuentra todos los usuarios que contengan el nombre
	 * @param nombre
	 * @param flash
	 * @return
	 */
	@GetMapping("/buscar")
	@ResponseBody
	public List<Usuario> listarUsuarioPorNombre(@RequestParam(name = "nombre") String nombre,
			RedirectAttributes flash) {

		String name = nombre;

		List<Usuario> usuarios;
		try {
			usuarios = usuarioService.findUsuarioByName(name);
			return usuarios;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, new Locale("es", "ES"));
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
	public String borrarUsuarioPorId(@PathVariable(name = "id") Integer id, RedirectAttributes flash) {
		try {
			usuarioService.deleteUsuarioById(id);
			flash.addFlashAttribute("success", "Se ha borrado el usuario");
			return "redirect:/usuarios/registro";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, new Locale("es", "ES"));
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
	public String modificarUsuario(@PathVariable(name = "id") Integer id, RedirectAttributes flash, Model model) {
		try {
			Usuario usuario = usuarioService.findUsuarioById(id);
			UsuarioDTO usuarioDto = new UsuarioDTO(usuario.getUsername());

			model.addAttribute("usuario", usuarioDto);
			flash.addFlashAttribute("success", "Se ha borrado el usuario");
			return "register";
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, new Locale("es", "ES"));
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
			ex.printStackTrace();
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}

}
