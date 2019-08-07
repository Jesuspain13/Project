package es.indra.censo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.indra.censo.dao.IComplejoDao;
import es.indra.censo.model.Complejo;
import es.indra.censo.model.Edificio;
import es.indra.censo.service.IRegistroService;

@Controller
@RequestMapping("/complejo")
public class ComplejoController {
	
	@Autowired
	private IComplejoDao complejoDao;
	
	@Autowired
	private IRegistroService registroService;

	@PostMapping("listar")
	public String listar(Complejo c, Model model) {
		Complejo complejo = complejoDao.findById(c.getId()).get();
		model.addAttribute("complejo", complejo);
		model.addAttribute("edificios", complejo.getEdificios());
		model.addAttribute("registro", complejo.getRegistro());
		Edificio edificio = new Edificio();
		model.addAttribute("edificio", edificio);
		
		
		return "searchform";
	}

}
