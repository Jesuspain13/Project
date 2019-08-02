package es.indra.censo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.model.Puesto;
import es.indra.censo.service.IDocReaderService;
import es.indra.censo.service.IPuestoService;

@Controller
@RequestMapping("/puesto")
@SessionAttributes("puesto")
public class PuestoController {

	@Autowired
	private IPuestoService puestoService;
	
	@Autowired
	private IDocReaderService docSvc;

	// Método para mostrar todos los puestos del censo.
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Distribución de los puestos de trabajo");
		model.addAttribute("puestos", puestoService.findAll());
		docSvc.readDocument();
		return "listar";

	}

	// Método para mostrar un puesto a través del Id.
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Puesto puesto = puestoService.findPuestoById(id);
		
		if (puesto == null) {
			flash.addFlashAttribute("error", "¡Lo sentimos, el puesto que está buscando no existe!");
			return "redirect:/listar";
		}
		
		model.put("puesto", puesto);
		model.put("titulo", "Este es el puesto número: " + puesto.getIdPuesto());

		return "ver";
	}
	
	

}
