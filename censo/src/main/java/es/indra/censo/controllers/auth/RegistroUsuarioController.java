package es.indra.censo.controllers.auth;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.dao.IRolDao;
import es.indra.censo.dao.IUsuarioDao;
import es.indra.censo.model.Rol;
import es.indra.censo.model.Usuario;

@Controller
@RequestMapping("/usuario")
public class RegistroUsuarioController {
	
	private Logger log = LoggerFactory.getLogger(RegistroUsuarioController.class);
	
	@Autowired
	private MessageSource msgSource;
	
	@Autowired
	private IRolDao rolDao;
	
	@Autowired
	private IUsuarioDao uDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	
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
	
	@PostMapping("/registro")
	public String registrarUsuario(UsuarioDTO usuarioDto, Model model, RedirectAttributes flash, Locale locale) {
		try {
			
			Usuario usuario = new Usuario();
			usuario.setUsername(usuarioDto.getUsername());
			String passBCryptEncoded = this.passwordEncoder
					.encode(usuarioDto.getPasswordDecoded());
			usuario.setPassword(passBCryptEncoded);
			usuario.setEnabled(true);
			this.comprobarPermiso(usuarioDto.getRol(), usuario);
			
			uDao.save(usuario);
			return "home";
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
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
