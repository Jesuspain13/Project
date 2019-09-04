package es.indra.censo.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.dao.IUeDao;
import es.indra.censo.dao.IUeRepercutibleDao;
import es.indra.censo.model.Edificio;
import es.indra.censo.model.Empleado;
import es.indra.censo.model.Ue;
import es.indra.censo.model.UeRepercutible;
import es.indra.censo.service.IEdificioService;

@Controller
@RequestMapping("/edificio")
@SessionAttributes({ "complejo", "registro", "idRegistro", "edificios", "departamentos", "subDptos"})
public class EdificioController {

	@Autowired
	private MessageSource msgSource;

	private Logger log = LoggerFactory.getLogger(EdificioController.class);

	@Autowired
	private IEdificioService edificioSvc;
	
	@Autowired
	private IUeRepercutibleDao ueRepDao;
	
	@Autowired
	private IUeDao ueDao;

	@PostMapping("listar")
	@Transactional
	public String listar(@Valid Edificio e, BindingResult resultValid,
			@RequestParam("idRegistro") Integer idRegistro,
			SessionStatus status, Map<String, Object> model,
			RedirectAttributes flash, Locale locale) {
		try {
			if (resultValid.hasErrors()) {

				return "searchform";
			}

			Edificio edificio = edificioSvc.findByIdEdificioAndRegistro(e.getIdEdificio(), idRegistro);
			List<UeRepercutible> departamentos = (List<UeRepercutible>) ueRepDao.findAllByIdRegistro(idRegistro);
			List<Ue> subDptos = (List<Ue>) ueDao.findAllByIdRegistro(idRegistro);
			status.setComplete();
			model.put("edificio", edificio);

			model.put("idRegistro", idRegistro);

			model.put("planta", edificio.getPlantas().get(1));
			model.put("departamentos", departamentos);
			model.put("subDptos", subDptos);
			model.put("empleado", new NuevoEmpleadoDTO());

			return "plantaprimera";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.msg", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/registro/listar";
		}
	}
}
