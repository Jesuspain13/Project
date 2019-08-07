package es.indra.censo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.indra.censo.dao.IEdificioDao;
import es.indra.censo.model.Edificio;

@Controller
@RequestMapping("/edificio")
public class EdificioController {
	
	private Logger log = LoggerFactory.getLogger(EdificioController.class);
	
	@Autowired
	private IEdificioDao edificioDao;

	@PostMapping("listar")
	public String listar(Edificio e, Model model) {
		try {
			
		
		Edificio edificio = edificioDao.findById(e.getIdEdificio()).get();
		model.addAttribute("edificio", edificio);
		model.addAttribute("planta", edificio.getPlantas().get(0));
		
		return "plantabaja";
		} catch(Exception ex) {
			log.error(ex.getMessage());
			model.addAttribute("error", UploadExcelController.ERROR_MSG);
			return "redirect:/registro/listar";
		}
	}
}
