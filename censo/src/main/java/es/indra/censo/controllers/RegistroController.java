package es.indra.censo.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.model.Complejo;
import es.indra.censo.model.Registro;
import es.indra.censo.service.IRegistroService;

@Controller
@RequestMapping("/registro")
@SessionAttributes({"registro", "complejos"})
public class RegistroController {
	private Logger log = LoggerFactory.getLogger(RegistroController.class);

	public static final String ERROR_NO_REGISTRO = "No hay registros. Llama al administrador para que inserte uno.";

	@Autowired
	private IRegistroService registroService;
	


	// Método para mostrar todos los registros del censo.
	@RequestMapping(value = { "/listar", "/" }, method = RequestMethod.GET)
	public String listar(Model model) {
		try {
			List<Registro> r = registroService.findAll();
			// CASO DE NO HABER REGISTROS
			if (r.size() < 1) {
				model.addAttribute("errorCard", ERROR_NO_REGISTRO);
				return "error/error_404";
			}
			Registro rSeleccionado = new Registro();
			model.addAttribute("titulo", "Listado de todos los registros");
			model.addAttribute("registro", rSeleccionado);
			model.addAttribute("registros", r);

			return "searchform";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			model.addAttribute("error", UploadExcelController.ERROR_MSG);
			return "redirect:/registro/listar";
		}
	}

	@PostMapping(value = "/listar")
	public String listar(Registro registro, Model model) {
		try {
			Registro rFound = registroService.findRegistroById(registro.getIdRegistro());
			model.addAttribute("titulo", "Listado de todos los registros");
			model.addAttribute("registro", rFound);
			model.addAttribute("complejos", rFound.getComplejos());
			Complejo c = new Complejo();
			model.addAttribute("complejo", c);

			return "searchform";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			model.addAttribute("error", UploadExcelController.ERROR_MSG);
			return "redirect:/registro/listar";
		}
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


}
