package es.indra.censo.controllers.auth;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.model.Usuario;
import es.indra.censo.service.IUsuarioService;

@Controller
@RequestMapping("/listausuarios")
public class UsuarioController {
	
	private Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private MessageSource msgSource;

	@GetMapping("/buscar")
	@ResponseBody
	public List<Usuario> listarUsuarioPorNombre(@RequestParam(name="nombre") String nombre, RedirectAttributes flash ) {
		
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
	
}



