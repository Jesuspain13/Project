package es.indra.censo.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.model.Puesto;
import es.indra.censo.service.IPuestoService;

@Controller
@RequestMapping("/puesto")
public class PuestoController {

	Logger log = LoggerFactory.getLogger(PuestoController.class);

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private IPuestoService puestoService;

	// Método para mostrar todos los puestos del censo.
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model, RedirectAttributes flash, Locale locale) {
		try {
			model.addAttribute("titulo", "Distribución de los puestos de trabajo");
			model.addAttribute("puestos", puestoService.findAll());

			return "redirect:/planta/listar";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}

	}

	// Método para mostrar un puesto a través del Id.
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash,
			Locale locale) {
		try {
			Puesto puesto = puestoService.findPuestoById(id);

			if (puesto == null) {
				flash.addFlashAttribute("error", "¡Lo sentimos, el puesto que está buscando no existe!");
				return "redirect:/listar";
			}

			model.put("puesto", puesto);
			model.put("titulo", "Este es el puesto número: " + puesto.getIdPuesto());

			return "ver";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}

}