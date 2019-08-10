package es.indra.censo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.indra.censo.model.Registro;
import es.indra.censo.service.IDocReaderService;
import es.indra.censo.service.IRegistroService;

@Controller
@RequestMapping("/doc")

public class UploadExcelController {
	
	public static final String SUCCESS_MSG = "Acción realizada con éxito.";
	public static final String ERROR_MSG = "Ha ocurrido un error. Acción no realizada.";
	
	Logger log = LoggerFactory.getLogger(UploadExcelController.class);
	
	@Autowired
	private IDocReaderService docReaderSvc;
	
	@Autowired
	private IRegistroService registroSvc;

	@GetMapping("/upload")
	@Secured({"ROLE_ADMIN"})
	public String uploadExcel(Model model) {
		Registro r = new Registro();
		model.addAttribute("registro", r);
		return "upload";
	}
	
	@PostMapping("/upload")
	@Secured({"ROLE_ADMIN"})
	public String uploadExcel(Registro r, Model model) {
		Registro registro = registroSvc.save(r);;
		model.addAttribute("registro", registro);
		return "upload";
	}
	
	@PostMapping("/upload/{id}")
	@Secured({"ROLE_ADMIN"})
	public String uploadExcel(@PathVariable("id") int idRegistro, @RequestParam("file") MultipartFile file, Model model) {
		try {
			docReaderSvc.readDocument(file, idRegistro);
			model.addAttribute("success", SUCCESS_MSG);
		    return "redirect:/registro/listar";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			model.addAttribute("error", ERROR_MSG);
			return "upload";
		}
	   
	}
}
