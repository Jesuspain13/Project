package es.indra.censo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import es.indra.censo.dao.IComplejoDao;
import es.indra.censo.model.Complejo;
import es.indra.censo.model.Edificio;
import es.indra.censo.service.IComplejoService;

@Controller
@RequestMapping("/complejo")
@SessionAttributes("idRegistro")
public class ComplejoController {

	private Logger log = LoggerFactory.getLogger(ComplejoController.class);

	@Autowired
	private IComplejoService complejoSvc;

	@PostMapping("listar")
	public String listar(Complejo c, @RequestParam("idRegistro") Integer idRegistro,
			Model model) {
		try {
			Complejo complejo = complejoSvc.findByIdAndRegistro(c.getId(), idRegistro);
			model.addAttribute("complejo", complejo);
			model.addAttribute("idRegistro", idRegistro);
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
