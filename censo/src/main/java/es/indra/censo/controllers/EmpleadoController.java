package es.indra.censo.controllers;

import java.util.Locale;
import java.util.Map;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.controllers.paginator.PageRender;
import es.indra.censo.model.Empleado;
import es.indra.censo.service.IEmpleadoService;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

	Logger log = LoggerFactory.getLogger(EmpleadoController.class);

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private IEmpleadoService empleadoService;

	// Método para mostrar todos los empleados del censo.
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model, RedirectAttributes flash, Locale locale) {
		try {
			model.addAttribute("titulo", "Listado de todos los empleados");
			model.addAttribute("empleados", empleadoService.findAll());

			return "listar";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}

	}

	// Método para mostrar el detalle el empleado que queramos por Id.
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash,
			Locale locale) {
		try {
			Empleado empleado = empleadoService.findEmpleadoById(id);

			if (empleado == null) {
				flash.addFlashAttribute("error", "¡El empleado al que intenta acceder no existe en la BBDD!");
				return "redirect:/listar";
			}

			model.put("empleado", empleado);
			model.put("titulo", "Información detallada de: " + empleado.getNombre() + " " + empleado.getApellido());
			return "ver";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}

	@GetMapping(value = "/info")
	@ResponseBody
	public Empleado verInfo(@RequestParam(name = "id") Integer id, Locale locale) {
		try {
			System.out.println("método buscar json");
			Empleado empleado = empleadoService.findEmpleadoByIdPuesto(id);

			if (empleado == null) {
				empleado = new Empleado();
				empleado.setNombre("VACIO");
			}

			System.out.println("previo fin de metodo, mandar resultado");
			return empleado;
		} catch (Exception ex) {
			log.error(ex.getMessage());

			return null;
		}
	}
	
	@GetMapping("/buscar/nombre")
	public String buscarPorNombre(Model model, RedirectAttributes flash, Locale locale) {
		try {
			return "listarempleado";
		}catch (Exception ex) {
			log.error(ex.getMessage());

			return null;
		}
	}
	
	
	@PostMapping("/buscar/nombre")
	public String buscarPorNombre(@RequestParam(name="nombre") String nombre,
			@RequestParam(name="apellido") String apellido,
			Model model, RedirectAttributes flash, Locale locale) {
		try {
			Page<Empleado> pageSelected = empleadoService.findEmpleadoByNombreYApellidos(nombre, apellido, 0);
			PageRender<Empleado> pgr = new PageRender<Empleado>("/buscar/nombre/", pageSelected);
			model.addAttribute("pageSelected", pageSelected);
			model.addAttribute("pageRender", pgr);
			
			return "listarempleado";
			 
		} catch (Exception ex) {
			log.error(ex.getMessage());

			return null;
		}
	}
	
	@GetMapping("/buscar/nombre/{page}")
	public String cambiarPáginaPaginador(@PathVariable(value="page") int pageNumber, Model model,
			RedirectAttributes flash, Locale locale) {
		try {
			Page<Empleado> pageSelected = empleadoService.findEmpleadoByNombreYApellidos(nombre, apellido, pageNumber);
			PageRender<Empleado> pgr = new PageRender<Empleado>("/buscar/nombre/", pageSelected);
			model.addAttribute("pageSelected", pageSelected);
			model.addAttribute("pageRender", pgr);
			
			return "listarEmpleado";
			 
		} catch (Exception ex) {
			log.error(ex.getMessage());

			return null;
		}
	}

}
