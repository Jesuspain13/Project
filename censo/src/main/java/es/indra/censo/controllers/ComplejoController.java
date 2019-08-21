package es.indra.censo.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.model.Complejo;
import es.indra.censo.model.Edificio;
import es.indra.censo.service.IComplejoService;

@Controller
@RequestMapping("/complejo")
@SessionAttributes({ "registro", "complejos", "idRegistro", "edificios" })
public class ComplejoController {

	private Logger log = LoggerFactory.getLogger(ComplejoController.class);

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private IComplejoService complejoSvc;

	@PostMapping("listar")
	public String listar(@Valid Complejo c, BindingResult resutlValid, @RequestParam("idRegistro") Integer idRegistro,
			Model model, RedirectAttributes flash, SessionStatus status) {
		try {
			if (resutlValid.hasErrors()) {

				return "searchform";
			}
			Complejo complejo = complejoSvc.findByIdAndRegistroWithJoinFetch(c.getId(), idRegistro);
			status.setComplete();
			model.addAttribute("complejo", complejo);
			model.addAttribute("idRegistro", idRegistro);
			model.addAttribute("edificios", complejo.getEdificios());
			model.addAttribute("registro", complejo.getRegistro());
			Edificio edificio = new Edificio();
			model.addAttribute("edificio", edificio);

			return "searchform";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			flash.addFlashAttribute("error", UploadExcelController.ERROR_MSG);
			return "redirect:/complejo/listar";
		}
	}

}
