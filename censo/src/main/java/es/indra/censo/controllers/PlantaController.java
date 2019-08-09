package es.indra.censo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.model.Planta;
import es.indra.censo.service.IPlantaService;

@Controller
@RequestMapping("/planta")
@SessionAttributes("planta")
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
	@ResponseBody
	public Planta ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Planta planta = plantaService.findPlantaById(id);


		model.put("planta", planta);
		model.put("titulo", "Esta usted en la planta: " + planta.getNombrePlanta());
		if (planta.getNombrePlanta().contains("0")) {

			return planta;

		}
		return planta;

		

	}

	// Método para mostrar la planta que queramos por Id.
		@PostMapping(value = "/ver")
		public String ver(Planta planta, Map<String, Object> model, RedirectAttributes flash) {

			Planta plantaEncontrada = plantaService.findPlantaById(planta.getId());

//			if (planta == null) {
//				flash.addFlashAttribute("error", "¡La planta a la que intenta acceder no existe!");
//				return "redirect:/listar";
//			}

			model.put("planta", plantaEncontrada);
			model.put("edificio", plantaEncontrada.getEdificio());
			model.put("titulo", "Esta usted en la planta: " + planta.getNombrePlanta());
			if (plantaEncontrada.getNombrePlanta().contains("0")) {
				return "plantabaja";
			}
			return "plantaprimera";

			

		}
}
