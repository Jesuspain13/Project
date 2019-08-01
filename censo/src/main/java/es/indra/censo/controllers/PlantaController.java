package es.indra.censo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.model.Planta;
import es.indra.censo.service.IPlantaService;

@Controller
@RequestMapping("/planta")
public class PlantaController {

	@Autowired
	private IPlantaService plantaService;

	// Método para mostrar todas las plantas de un edificio.
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Plantas");
		model.addAttribute("plantas", plantaService.findAll());

		return "listar";

	}

	// Método para mostrar la planta que queramos por Id.
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Planta planta = plantaService.findPlantaById(id);

		if (planta == null) {
			flash.addFlashAttribute("error", "¡La planta a la que intenta acceder no existe!");
			return "redirect:/listar";
		}

		model.put("planta", planta);
		model.put("titulo", "Esta usted en la planta: " + planta.getNombrePlanta());
		return "ver";

	}

}
