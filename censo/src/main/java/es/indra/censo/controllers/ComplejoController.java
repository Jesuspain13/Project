package es.indra.censo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.indra.censo.dao.IComplejoDao;
import es.indra.censo.docreader.ReaderFromView;
import es.indra.censo.model.Complejo;
import es.indra.censo.model.Edificio;
import es.indra.censo.service.IRegistroService;

@Controller
@RequestMapping("/complejo")
public class ComplejoController {
	
	private Logger log = LoggerFactory.getLogger(ComplejoController.class);
	
	@Autowired
	private IComplejoDao complejoDao;
	
	@Autowired
	private IRegistroService registroService;

	@PostMapping("listar")
	public String listar(Complejo c, Model model) {
		try {
			Complejo complejo = complejoDao.findById(c.getId()).get();
			model.addAttribute("complejo", complejo);
			model.addAttribute("edificios", complejo.getEdificios());
			model.addAttribute("registro", complejo.getRegistro());
			Edificio edificio = new Edificio();
			model.addAttribute("edificio", edificio);
			
			
			return "searchform";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			model.addAttribute("error", UploadExcelController.ERROR_MSG);
			return "redirect:/complejo/listar";
		}
	}

}
