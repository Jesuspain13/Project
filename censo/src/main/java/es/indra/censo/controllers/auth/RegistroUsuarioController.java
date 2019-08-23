package es.indra.censo.controllers.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.dao.IRolDao;
import es.indra.censo.model.Rol;

@Controller
@RequestMapping("/usuario")
public class RegistroUsuarioController {
	
	private Logger log = LoggerFactory.getLogger(RegistroUsuarioController.class);
	
	@Autowired
	private MessageSource msgSource;
	
	@Autowired
	private IRolDao rolDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsManager userDetailsManager;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@GetMapping("/registro")
	public String registrarUsuario(Model model, RedirectAttributes flash, Locale locale) {
		try {
			UsuarioDTO usuario = new UsuarioDTO();
			model.addAttribute("usuario", usuario);
			return "register";
		} catch (Exception ex) {
			log.error("ex");
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}
	
	@PostMapping("/registro")
	public String registrarUsuario(UsuarioDTO usuarioDTO, Model model, RedirectAttributes flash, Locale locale) {
		try {
			Rol rol =  rolDao.findById(usuarioDTO.getRol()).get();
			List<Rol> rolesList = new ArrayList<Rol>();
			rolesList.add(rol);
			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			auths.add(new SimpleGrantedAuthority(rol.getAuthority()));
			//Usuario userWithData = usuarioDTO.crearUsuario(rol);
			String passBCryptEncoded = this.passwordEncoder
					.encode(usuarioDTO.getPasswordDecoded());
			UserDetails user = new User(usuarioDTO.getUsername(), passBCryptEncoded, auths);
			userDetailsManager.createUser(user);
			//model.addAttribute("usuario", usuario);
			return "registro";
		} catch (Exception ex) {
			log.error("ex");
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}
 
}
