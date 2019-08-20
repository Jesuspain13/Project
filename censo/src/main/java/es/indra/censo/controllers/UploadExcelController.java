package es.indra.censo.controllers;

import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.docreader.FileWrapper;
import es.indra.censo.model.Registro;
import es.indra.censo.service.IDocReaderService;
import es.indra.censo.service.IRegistroService;

@Controller
@RequestMapping("/doc")
@SessionAttributes("fileWrapper")
public class UploadExcelController {
	
	@Autowired
	private MessageSource msgSource;
	
	public static final String SUCCESS_MSG = "Acción realizada con éxito.";
	public static final String ERROR_MSG = "Ha ocurrido un error. Acción no realizada.";
	
	Logger log = LoggerFactory.getLogger(UploadExcelController.class);
	
	@Autowired
	private IDocReaderService docReaderSvc;
	
	@Autowired
	private IRegistroService rService;


	@GetMapping("/upload")
	@Secured({"ROLE_ADMIN"})
	public String uploadExcel(Model model, RedirectAttributes flash) {
		//Registro r = new Registro();
		FileWrapper r = new FileWrapper();
		model.addAttribute("fileWrapper", r);
		return "upload";
	}
	
//	@PostMapping("/upload")
//	@Secured({"ROLE_ADMIN"})
//	public String uploadExcel(@Valid FileWrapper r, BindingResult resultValid,
//			Model model, RedirectAttributes flash, Locale locale) {
//		if (resultValid.hasErrors()) {
//			return "upload";
//		}
//		
//		Registro rSearched = null;
//		rSearched = rService.findRegistroByVersion(r.getVersion());
//		if (rSearched != null) {
//			flash.addAttribute("error", "La versión ya existe");
//			return "redirect:/doc/upload";
//		}
//		//Registro registro = registroSvc.save(r);;
//		model.addAttribute("registro", r);
//		return "upload";
//	}
	
//	@PostMapping("/upload/{version}")
//	@Secured({"ROLE_ADMIN"})
//	public String uploadExcel(@PathVariable("version") String version, @RequestParam("file") MultipartFile file,
//			Model model, RedirectAttributes flash, Locale locale) {
//		try {
//
//			docReaderSvc.readDocument(file, version, locale);
//			flash.addFlashAttribute("success", SUCCESS_MSG);
//		    return "redirect:/registro/listar";
//		} catch (Exception ex) {
//			log.error(ex.getMessage());
//			flash.addFlashAttribute("error", "Error en la lectura del archivo.\n"
//					+ "Error concreto:" + ex.getMessage());
//			return "redirect:/doc/upload";
//		}
//	   
//	}
	
	@PostMapping("/upload")
	@Secured({"ROLE_ADMIN"})
	public String uploadExcel(@Valid FileWrapper r,
			BindingResult resultValid, Model model, RedirectAttributes flash, Locale locale) {
		try {
			if (resultValid.hasErrors()) {
				return "upload";
			}
			//si la versión ya existe vuelve hacia atrás.
			Registro rSearched = null;
			rSearched = rService.findRegistroByVersion(r.getVersion());
			if (rSearched != null) {
				flash.addFlashAttribute("error", msgSource
						.getMessage("text.registro.error.version.duplicada", null, locale));
				return "redirect:/doc/upload";
			}
			docReaderSvc.readDocument(r.getFile(), r.getVersion(), locale);
			flash.addFlashAttribute("success", SUCCESS_MSG);
		    return "redirect:/registro/listar";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			flash.addFlashAttribute("error", "Error en la lectura del archivo.\n"
					+ "Error concreto:" + ex.getMessage());
			return "redirect:/doc/upload";
		}
	   
	}
}
