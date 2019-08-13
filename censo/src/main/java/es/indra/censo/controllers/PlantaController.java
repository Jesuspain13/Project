package es.indra.censo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.Registro;
import es.indra.censo.model.wrapper.NoSorteableException;
import es.indra.censo.model.wrapper.PlantaBajaWrapper;
import es.indra.censo.service.IPlantaService;
import es.indra.censo.service.IPuestoService;
import es.indra.censo.service.IRegistroService;

@Controller
@RequestMapping("/planta")
@SessionAttributes({"planta", "idRegistro"})
public class PlantaController {

	private Logger log = LoggerFactory.getLogger(ComplejoController.class);

	@Autowired
	private IPlantaService plantaService;

	@Autowired
	private IPuestoService puestoService;
	
	@Autowired
	private IRegistroService registroSvc;

	/**
	 * Método para la función javascript que recoge los puestos
	 * 
	 * @param nombrePlanta nombre de la planta a buscar
	 * @param model
	 * @param flash
	 * @return
	 */
//	@GetMapping(value = "/ver/{nombrePlanta}")
//	@ResponseBody
//	public List<Puesto> ver(@PathVariable(value = "nombrePlanta") Integer nombrePlanta, Map<String, Object> model,
//			RedirectAttributes flash) {
//		try {
//
//			Planta planta = plantaService.findPlantaByNombrePlanta(nombrePlanta);
//	
//			List<Puesto> puestos = (puestoService.findByPlantaOrdenados(planta));
//	
//			return puestos;
//		}catch (NoSorteableException ex)  {
//			log.error(ex.getMessage());
//			model.put("error", "No se ha podido ordenar la lista de puestos");
//			return new ArrayList<Puesto>(Arrays.asList(new Puesto()));
//		} catch (Exception ex)  {
//			log.error(ex.getMessage());
//			model.put("error", ex.getMessage());
//			return new ArrayList<Puesto>(Arrays.asList(new Puesto()));
//		} 
//
//	}
	
	@RequestMapping(value = "/ver/{nombrePlanta}", method=RequestMethod.GET)
	@ResponseBody
	public List<Puesto> verPlantaPorRegistro(
			@PathVariable(value = "nombrePlanta") Integer nombrePlanta,
			@RequestParam("idRegistro") Integer idRegistro,
			Map<String, Object> model,
			RedirectAttributes flash) {
		try {


//			Planta plantaEncontrada = plantaService
//					.findPlantaByIdPlantaAndRegistro(nombrePlanta, idRegistro);

	
			List<Puesto> puestos = (puestoService.findByPlantaOrdenados(nombrePlanta, idRegistro));
	
			return puestos;
		}catch (NoSorteableException ex)  {
			log.error(ex.getMessage());
			model.put("error", "No se ha podido ordenar la lista de puestos");
			return new ArrayList<Puesto>(Arrays.asList(new Puesto()));
		} catch (Exception ex)  {
			log.error(ex.getMessage());
			model.put("error", ex.getMessage());
			return new ArrayList<Puesto>(Arrays.asList(new Puesto()));
		} 

	}

	// Método para mostrar la planta que queramos por Id.
	@PostMapping(value = "/ver")
	public String ver(Planta planta, 
			@RequestParam("idRegistro") Integer idRegistro, Map<String, Object> model, RedirectAttributes flash) {
		try {
			Planta plantaEncontrada = plantaService
					.findPlantaByIdPlantaAndRegistro(planta.getId(), idRegistro);

			if (plantaEncontrada == null) {
				flash.addFlashAttribute("error", "¡La planta a la que intenta acceder no existe!");
				return "redirect:/listar";
			}

			model.put("planta", plantaEncontrada);
			model.put("edificio", plantaEncontrada.getEdificio());
			model.put("idRegistro", idRegistro);
			model.put("titulo", "Esta usted en la planta: " + planta.getNombrePlanta());
			if (plantaEncontrada.getNombrePlanta().contains("0")) {
				return "plantabaja";
			}
			return "plantaprimera";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			model.put("error", "Error al encontrar la planta");
			return "redirect:/registro/listar";
		}

	}

	// Método para mostrar la planta que queramos por Id.
	@GetMapping(value = "/ver/azahar")
	public String verPlantaAzahar(@RequestParam("idRegistro") Integer idRegistro,
			Map<String, Object> model, RedirectAttributes flash) {
		try {
			PlantaBajaWrapper pBajaWrapper = new PlantaBajaWrapper();
			Planta plantaEncontrada = plantaService.findPlantaByNombrePlantaAndRegistro(0, idRegistro);

			if (plantaEncontrada == null) {
				flash.addFlashAttribute("error", "Error al encontrar la planta");
				return "redirect:/registro/listar";
			}
			pBajaWrapper.ordenarPuesto(plantaEncontrada.getNombrePlanta(), plantaEncontrada.getPuestos());

			model.put("planta", plantaEncontrada);
			model.put("edificio", plantaEncontrada.getEdificio());
			model.put("puestos", pBajaWrapper.getPlantaAzahara());
			model.put("idRegistro", idRegistro);
			model.put("titulo", "Esta usted en la planta: " + plantaEncontrada.getNombrePlanta());

			return "plantazahar";
		} catch (NoSorteableException ex)  {
			log.error(ex.getMessage());
			model.put("error", "No se ha podido ordenar la lista de puestos");
			return "redirect:/registro/listar";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			model.put("error", "Error al encontrar la planta");
			return "redirect:/registro/listar";
		}

	}
}
