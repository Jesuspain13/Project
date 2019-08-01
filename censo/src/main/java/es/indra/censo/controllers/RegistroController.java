package es.indra.censo.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.model.Registro;
import es.indra.censo.service.IRegistroService;


@Controller
@RequestMapping("/registro")
public class RegistroController {

	@Autowired
	private IRegistroService registroService;

	// Método para mostrar todos los registros del censo.
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de todos los registros");
		model.addAttribute("registros", registroService.findAll());

		return "listar";

	}

	// Método para mostrar el detalle de los registros por Id.
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Registro registro = registroService.findRegistroById(id);

		if (registro == null) {
			flash.addFlashAttribute("error", "¡El registro al que intenta acceder no existe en la BBDD!");
			return "redirect:/listar";
		}

		model.put("registro", registro);
		model.put("titulo", "Información detallada del registro número: " + registro.getVersion());
		return "ver";

	}

	// Método para guardar un nuevo registro.
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Registro registro, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Nuevo Registro");
			return "form";
		}

		registroService.save(registro);
		flash.addFlashAttribute("success", "¡Se ha creado el nuevo registro con éxito!");
		return "redirect:listar";
	}

}
