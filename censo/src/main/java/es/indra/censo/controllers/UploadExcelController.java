package es.indra.censo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.indra.censo.service.IDocReaderService;

@Controller
@RequestMapping("/doc")
public class UploadExcelController {
	
	public static final String SUCCESS_MSG = "Acción realizada con éxito.";
	public static final String ERROR_MSG = "Ha ocurrido un error. Acción no realizada.";
	
	Logger log = LoggerFactory.getLogger(UploadExcelController.class);
	
	@Autowired
	private IDocReaderService docReaderSvc;
	
	@GetMapping("/upload")
	public String uploadExcel() {
		return "upload";
	}
	
	@PostMapping("/upload")
	public String uploadExcel(@RequestParam("file") MultipartFile file, Model model) {
		try {
			docReaderSvc.readDocument(file);
			model.addAttribute("success", SUCCESS_MSG);
		    return "redirect:/registro/listar";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			model.addAttribute("error", ERROR_MSG);
			return "upload";
		}
	   
	}
}
