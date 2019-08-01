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

import es.indra.censo.model.Empleado;
import es.indra.censo.service.IEmpleadoService;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {
	
	@Autowired
	private IEmpleadoService empleadoService;
	
	//Método para mostras todos los empleados del censo.
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de todos los empleados");
		model.addAttribute("empleados", empleadoService.findAll());

		return "listar";

	}
	
	// Método para mostrar el detalle el empleado que queramos por Id.
		@GetMapping(value = "/ver/{id}")
		public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

			Empleado empleado = empleadoService.findEmpleadoById(id);

			if (empleado == null) {
				flash.addFlashAttribute("error", "¡El empleado al que intenta acceder no existe en la BBDD!");
				return "redirect:/listar";
			}

			model.put("empleado", empleado);
			model.put("titulo", "Información detallada de: " + empleado.getNombre() + " " + empleado.getApellido());
			return "ver";

		}
	
	

}
