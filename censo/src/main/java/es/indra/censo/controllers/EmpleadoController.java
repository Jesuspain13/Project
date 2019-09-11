package es.indra.censo.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.controllers.paginator.PageRender;
import es.indra.censo.dao.IUeDao;
import es.indra.censo.dao.IUeRepercutibleDao;
import es.indra.censo.model.Edificio;
import es.indra.censo.model.Empleado;
import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.Registro;
import es.indra.censo.model.Ue;
import es.indra.censo.model.UeRepercutible;
import es.indra.censo.service.IEmpleadoService;
import es.indra.censo.service.IPuestoService;
import es.indra.censo.service.IRegistroService;

@Controller
@RequestMapping("/empleado")
@SessionAttributes({"nombreBuscado", "apellidoBuscado", "idRegistro", "registros"})
public class EmpleadoController {

	Logger log = LoggerFactory.getLogger(EmpleadoController.class);

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private IEmpleadoService empleadoService;
	
	@Autowired
	private IRegistroService registroSvc;
	
	@Autowired
	private IUeRepercutibleDao ueRepDao;
	
	@Autowired
	private IPuestoService puestoSvc;
	
	@Autowired
	private IUeDao ueDao;

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
			List<Registro> registrosEncontrados = registroSvc.findAll();
			
			
			//Miramos si hay registros, si existe te deja buscar empleados, sino te devuelve el error 404.
			if(registrosEncontrados.size() > 0) {
				model.addAttribute("registros", registrosEncontrados);
				return "listarempleados";
			}else {
				
				return "error/error_404";
				
			}
			
		}catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
		
	}
	
	
	@PostMapping("/buscar/nombre")
	public String buscarPorNombre(@RequestParam(name="nombre") String nombre,
			@RequestParam(name="apellidos") String apellido,
			@RequestParam(name="registro") int registro,
			Model model, RedirectAttributes flash, Locale locale) {
		try {
			int r = registro;
			System.out.println(r);
			Page<Empleado> pageSelected = empleadoService.findEmpleadoByNombreYApellidos(registro, nombre, apellido, 0);
			PageRender<Empleado> pgr = new PageRender<Empleado>("/empleado/buscar/nombre/pagina", pageSelected);
			model.addAttribute("pageSelected", pageSelected);
			model.addAttribute("page", pgr);
			model.addAttribute("nombreBuscado", nombre);
			model.addAttribute("apellidoBuscado", apellido);
			model.addAttribute("idRegistro", registro);
			
			return "listarempleados";
			 
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}
	
	@GetMapping("/buscar/nombre/pagina")
	public String cambiarPáginaPaginador(@RequestParam(name="page") int page,
			@RequestParam(name="nombre") String nombre,
			@RequestParam(name="apellidos") String apellido,
			@RequestParam(name="registro") int registro,
			Model model, RedirectAttributes flash, Locale locale) {
		try {
			System.out.println(registro);
			Page<Empleado> pageSelected = empleadoService.findEmpleadoByNombreYApellidos(registro, nombre, apellido, page);
			PageRender<Empleado> pgr = new PageRender<Empleado>("/buscar/nombre/pagina", pageSelected);
			model.addAttribute("pageSelected", pageSelected);
			model.addAttribute("page", pgr);
			model.addAttribute("idRegistro", registro);
			
			return "listarempleados";
			 
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}
	
	//metodo buscar usuario por id con puesto y planta para el ver ubicacion
	@GetMapping("/buscar/ubicacion")
	public String verUbicacionEmpleado(@RequestParam(name="idEmpleado") int idEmpleado,
			@RequestParam(name="idRegistro") int idRegistro,
			Model model, RedirectAttributes flash, Locale locale) {
		try {
			Empleado empleadoSeleccionado = empleadoService.findEmpleadoByIdWithPuestoAndPlanta(idEmpleado, idRegistro);
			Planta plantaDelEmpleado = empleadoSeleccionado.getPuesto().getPlanta();
			List<Puesto> puestos = puestoSvc.findByPlantaOrdenados(plantaDelEmpleado.getNombrePlanta(), idRegistro);
			Edificio edificioDelEmpleado = plantaDelEmpleado.getEdificio();
			List<UeRepercutible> departamentos = (List<UeRepercutible>) ueRepDao.findAllByIdRegistro(idRegistro);
			List<Ue> subDptos = (List<Ue>) ueDao.findAllByIdRegistro(idRegistro);
			
			model.addAttribute("edificio", edificioDelEmpleado);
			model.addAttribute("idRegistro", empleadoSeleccionado.getRegistro().getIdRegistro());
			model.addAttribute("planta", plantaDelEmpleado);
			model.addAttribute("puestos", puestos);
			model.addAttribute("empleadoSeleccionado", empleadoSeleccionado);
			model.addAttribute("departamentos", departamentos);
			model.addAttribute("subDptos", subDptos);
			model.addAttribute("empleado", new NuevoEmpleadoDTO());
			
			if (plantaDelEmpleado.getNombrePlanta().equals("0")) {
				return "plantabaja";
			} else if (plantaDelEmpleado.getNombrePlanta().equals("1")) {
				return "plantaprimera";
			} else {
				return "plantazahar";
			}
			
		}catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}
	
	@PostMapping("/nuevo")
	@Transactional
	public String guardarEmpleado(@Valid NuevoEmpleadoDTO empleado, BindingResult resultValid, Model model,
			RedirectAttributes flash, Locale locale) {
		String planta = null;
		try {
			
			//buscamos el puesto y la ue seleccionada
			Puesto puestoAModificar = puestoSvc.findPuestoByIdAndRegistro(empleado.getPuesto(), empleado.getRegistro());
			planta =  puestoAModificar.getPlanta().getNombrePlanta();
			Registro registro = puestoAModificar.getRegistro();
			String urlRedirect = "redirect:/planta/mostrar/%s?idRegistro=%s";
			
			// si hay errores de validación
			if (resultValid.hasErrors()) {
				String error = this.formatearListaErroresValidacion(resultValid.getAllErrors(), locale);
				flash.addFlashAttribute("error", error);
				return String.format(urlRedirect, planta, empleado.getRegistro());
			}
			
			Ue ue = ueDao.findByIdUe(empleado.getUe());
			System.out.println("ue: "+ ue.getNombreUe());
			//creamos el empleado y asignamos los campos
			Empleado empleadoGuardado = new Empleado(empleado.getNumero(), empleado.getNick(),
					empleado.getNombre(), empleado.getApellidos(), registro, ue );
			puestoAModificar.setEmpleado(empleadoGuardado);
			puestoAModificar.setOcupado(true);
			puestoSvc.save(puestoAModificar);
			
			flash.addFlashAttribute("idRegistro", registro);
			return String.format(urlRedirect, planta, empleado.getRegistro());
		}catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			if (planta != null) {
				return "redirect:/planta/ver/" + planta;
			} else {
				return "redirect:/";
			}
			
		}
	}
	
	private String formatearListaErroresValidacion(List<ObjectError> errores, Locale locale) {
		String resultado = "";
		String inicioError = msgSource.getMessage("text.error.empleado.formulario.creacion", null, locale);
		for (ObjectError e: errores) {
			
			System.out.println(e.toString());
			//resultado += + " \n";
		}
		String error = String.format(inicioError, resultado);
		return error;
	}

}
