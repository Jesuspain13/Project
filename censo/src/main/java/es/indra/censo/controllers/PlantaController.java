package es.indra.censo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.dao.IUeDao;
import es.indra.censo.dao.IUeRepercutibleDao;
import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.Ue;
import es.indra.censo.model.UeRepercutible;
import es.indra.censo.model.wrapper.NoSorteableException;
import es.indra.censo.model.wrapper.PlantaBajaWrapper;
import es.indra.censo.service.IPlantaService;
import es.indra.censo.service.IPuestoService;

@Controller
@RequestMapping("/planta")
@SessionAttributes({ "planta", "idRegistro" })
public class PlantaController {

	private Logger log = LoggerFactory.getLogger(ComplejoController.class);

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private IPlantaService plantaService;

	@Autowired
	private IPuestoService puestoService;
	
	
	
	@Autowired
	private IUeRepercutibleDao ueRepDao;
	
	@Autowired
	private IUeDao ueDao;

	@RequestMapping(value = "/ver/{nombrePlanta}", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<Puesto> verPlantaPorRegistro(@PathVariable(value = "nombrePlanta") Integer nombrePlanta,
			@RequestParam("idRegistro") Integer idRegistro, Map<String, Object> model, RedirectAttributes flash) {
		try {

			List<Puesto> puestos = puestoService.findByPlantaOrdenados(nombrePlanta.toString(), idRegistro);


			return puestos;
		} catch (NoSorteableException ex) {
			log.error(ex.getMessage());
			model.put("error", "No se ha podido ordenar la lista de puestos");
			return new ArrayList<Puesto>(Arrays.asList(new Puesto()));
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, new Locale("es", "ES"));
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return null;
		}

	}

	// Método para mostrar la planta que queramos por Id.
	@PostMapping(value = "/ver")
	public String ver(Planta planta, @RequestParam("idRegistro") Integer idRegistro, Map<String, Object> model,
			RedirectAttributes flash, SessionStatus status, Locale locale) {
		try {
			Planta plantaEncontrada = plantaService.findPlantaByIdPlantaAndRegistro(planta.getId(), idRegistro);
			List<UeRepercutible> departamentos = (List<UeRepercutible>) ueRepDao.findAllByIdRegistro(idRegistro);
			List<Ue> subDptos = (List<Ue>) ueDao.findAllByIdRegistro(idRegistro);;
			if (plantaEncontrada == null) {
				flash.addFlashAttribute("error", "¡La planta a la que intenta acceder no existe!");
				return "redirect:/listar";
			}
			status.setComplete();
			model.put("planta", plantaEncontrada);
			model.put("edificio", plantaEncontrada.getEdificio());
			model.put("idRegistro", idRegistro);
			model.put("departamentos", departamentos);
			model.put("subDptos", subDptos);
			model.put("empleado", new NuevoEmpleadoDTO());
			model.put("titulo", "Esta usted en la planta: " + planta.getNombrePlanta());
			if (plantaEncontrada.getNombrePlanta().contains("0")) {
				return "plantabaja";
			}
			return "plantaprimera";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			flash.addFlashAttribute("error", msgSource.getMessage("text.error.encontrar.planta", null, locale));
			return "redirect:/registro/listar";
		}

	}
	
	
		@GetMapping(value = "/mostrar/{nombrePlanta}")
		public String ver(@PathVariable(value = "nombrePlanta") Integer nombrePlanta,
				@RequestParam(name="idRegistro") int idRegistro,
				Map<String, Object> model,
				RedirectAttributes flash, SessionStatus status, Locale locale) {
			try {

				Planta plantaEncontrada = plantaService.findPlantaByNombrePlantaAndRegistro(nombrePlanta, idRegistro);
				List<UeRepercutible> departamentos = (List<UeRepercutible>) ueRepDao.findAllByIdRegistro(idRegistro);
				List<Ue> subDptos = (List<Ue>) ueDao.findAllByIdRegistro(idRegistro);
				if (plantaEncontrada == null) {
					flash.addFlashAttribute("error", "¡La planta a la que intenta acceder no existe!");
					return "redirect:/listar";
				}
				status.setComplete();
				model.put("planta", plantaEncontrada);
				model.put("edificio", plantaEncontrada.getEdificio());
				model.put("idRegistro", plantaEncontrada.getRegistro().getIdRegistro());
				model.put("departamentos", departamentos);
				model.put("subDptos", subDptos);
				model.put("empleado", new NuevoEmpleadoDTO());
				model.put("titulo", "Esta usted en la planta: " + plantaEncontrada.getNombrePlanta());
				if (plantaEncontrada.getNombrePlanta().contains("0")) {
					return "plantabaja";
				}
				return "plantaprimera";
			} catch (Exception ex) {
				log.error(ex.getMessage());
				flash.addFlashAttribute("error", msgSource.getMessage("text.error.encontrar.planta", null, locale));
				return "redirect:/registro/listar";
			}

		}

	// Método para mostrar la planta que queramos por Id.
	@GetMapping(value = "/ver/azahar")
	public String verPlantaAzahar(@RequestParam("idRegistro") Integer idRegistro, Map<String, Object> model,
			RedirectAttributes flash, Locale locale) {
		try {

			List<Puesto> puestosPlantaAzahar = puestoService.findByPlantaOrdenados("azahar", idRegistro);

			if (puestosPlantaAzahar == null) {
				flash.addFlashAttribute("error", "Error al encontrar la planta");
				return "redirect:/registro/listar";
			}
			Puesto p = puestosPlantaAzahar.get(0);

			model.put("planta", p.getPlanta());
			model.put("edificio", p.getPlanta().getEdificio());
			model.put("puestos", puestosPlantaAzahar);
			model.put("idRegistro", idRegistro);
			model.put("titulo", "Esta usted en la planta: " + p.getPlanta().getNombrePlanta());

			return "plantazahar";
		} catch (NoSorteableException ex) {
			log.error(ex.getMessage());
			model.put("error", "No se ha podido ordenar la lista de puestos");
			return "redirect:/registro/listar";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			flash.addFlashAttribute("error", msgSource.getMessage("text.error.encontrar.planta", null, locale));
			return "redirect:/registro/listar";
		}

	}
}
