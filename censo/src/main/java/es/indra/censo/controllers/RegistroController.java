package es.indra.censo.controllers;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.model.Complejo;
import es.indra.censo.model.Registro;
import es.indra.censo.service.IRegistroService;

@Controller
@RequestMapping("/registro")
@SessionAttributes({ "registro", "complejos", "complejo" })
public class RegistroController {

	private Logger log = LoggerFactory.getLogger(RegistroController.class);

	@Autowired
	private IRegistroService registroService;

	@Autowired
	private MessageSource msgSource;

	@GetMapping(value = "/ver")
	public String verTodos(Model model, RedirectAttributes flash, Locale locale) {
		try {
			List<Registro> registros = registroService.findAll();
			model.addAttribute("registros", registros);
			return "listaregistros";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}

	// Método para mostrar todos los registros del censo.
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model, RedirectAttributes flash, Locale locale) {
		try {
			List<Registro> r = registroService.findAll();
			// CASO DE NO HABER REGISTROS
			if (r.size() < 1) {

				return "error/error_404";
			}
			Registro rSeleccionado = new Registro();
			model.addAttribute("titulo", "Listado de todos los registros");
			model.addAttribute("registro", rSeleccionado);
			model.addAttribute("registros", r);

			return "searchform";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}

	@PostMapping(value = "/listar")
	public String listar(Registro registro, Model model, SessionStatus status, RedirectAttributes flash,
			Locale locale) {
		try {
			Registro rFound = registroService.findByIdWithJoinFetch(registro.getIdRegistro());
			status.setComplete();
			model.addAttribute("titulo", "Listado de todos los registros");

			model.addAttribute("complejos", rFound.getComplejos());
			Complejo c = new Complejo();
			model.addAttribute("complejo", c);

			model.addAttribute("registro", rFound);
			return "searchform";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}

	// Método para mostrar el detalle de los registros por Id.
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash,
			Locale locale) {
		try {
			Registro registro = registroService.findRegistroById(id);

			if (registro == null) {
				flash.addFlashAttribute("error", msgSource.getMessage("text.error.encontrar.registro", null, locale));
				return "redirect:/listar";
			}

			model.put("registro", registro);
			model.put("titulo", "Información detallada del registro número: " + registro.getVersion());
			return "ver";

		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}

	}

	@PostMapping(value = "/borrar")
	public String borrar(@RequestParam(name = "id") Integer id, Model model, RedirectAttributes flash, Locale locale) {
		try {
			registroService.deleteRegistroById(id);
			return "redirect:/registro/ver";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}
}
