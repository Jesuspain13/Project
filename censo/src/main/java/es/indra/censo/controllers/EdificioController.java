package es.indra.censo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.indra.censo.dao.IEdificioDao;
import es.indra.censo.dao.IPuestoDao;
import es.indra.censo.model.Edificio;
import es.indra.censo.model.Planta;
import es.indra.censo.model.Puesto;
import es.indra.censo.model.wrapper.PlantaBajaWrapper;
import es.indra.censo.model.wrapper.PlantaWrapperAbs;

@Controller
@RequestMapping("/edificio")
public class EdificioController {
	
	private Logger log = LoggerFactory.getLogger(EdificioController.class);
	
	@Autowired
	private IEdificioDao edificioDao;
	
	@Autowired
	private IPuestoDao puestoDao;

	@PostMapping("listar")
	@Transactional
	public String listar(Edificio e, Model model) {
		try {
			
		
		Edificio edificio = edificioDao.findById(e.getIdEdificio()).get();
		model.addAttribute("edificio", edificio);
		//model.addAttribute("planta", edificio.getPlantas().get(0));
//		Planta p = edificio.getPlantas().get(1);
//		PlantaWrapperAbs pWrapper = new PlantaWrapper();
//		List<Puesto> puestosDesordenados = puestoDao.findByPlanta(p);
//		List<Puesto >puestos = (pWrapper.ordenarPuesto(puestosDesordenados));
//		p.setPuestos(puestos);
		
		Planta p = edificio.getPlantas().get(0);
		PlantaWrapperAbs pWrapper = new PlantaBajaWrapper();
		List<Puesto> puestosDesordenados = puestoDao.findByPlanta(p);
		List<Puesto >puestos = (pWrapper.ordenarPuesto(puestosDesordenados));
		p.setPuestos(puestos);
		
		//p.setPuestos(pWrapper.getPuestosOrdenados());
		model.addAttribute("planta", p);
		
		return "plantabaja";
		} catch(Exception ex) {
			log.error(ex.getMessage());
			model.addAttribute("error", UploadExcelController.ERROR_MSG);
			return "redirect:/registro/listar";
		}
	}
}
