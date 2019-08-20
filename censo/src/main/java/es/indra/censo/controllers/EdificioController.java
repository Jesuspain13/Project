package es.indra.censo.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import es.indra.censo.dao.IPuestoDao;
import es.indra.censo.model.Edificio;
import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.wrapper.PlantaWrapper;
import es.indra.censo.model.wrapper.PlantaWrapperAbs;
import es.indra.censo.service.IEdificioService;

@Controller
@RequestMapping("/edificio")
@SessionAttributes({"complejo", "registro", "idRegistro", "edificios"})
public class EdificioController {
	
	private Logger log = LoggerFactory.getLogger(EdificioController.class);
	
	@Autowired
	private IEdificioService edificioSvc;
	
	@Autowired
	private IPuestoDao puestoDao;

	@PostMapping("listar")
	@Transactional
	public String listar(@Valid Edificio e, BindingResult resultValid,
			@RequestParam("idRegistro") Integer idRegistro,
			SessionStatus status, Map<String, Object> model) {
		try {
				if (resultValid.hasErrors()) {
				
				return "searchform";
			}

		Edificio edificio = edificioSvc.findByIdEdificioAndRegistro(e.getIdEdificio(), idRegistro);
		status.setComplete();
		model.put("edificio", edificio);

		Planta p = edificio.getPlantas().get(1);
		PlantaWrapperAbs pWrapper = new PlantaWrapper();
		List<Puesto> puestosDesordenados = puestoDao.findByPlanta(p);
		List<Puesto >puestos = (pWrapper.ordenarPuesto(p.getNombrePlanta(), puestosDesordenados));
		p.setPuestos(puestos);
		
		model.put("idRegistro", idRegistro);
		
		model.put("planta", p);
		
		return "plantaprimera";
		} catch(Exception ex) {
			log.error(ex.getMessage());
			model.put("error", UploadExcelController.ERROR_MSG);
			return "redirect:/registro/listar";
		}
	}
}
