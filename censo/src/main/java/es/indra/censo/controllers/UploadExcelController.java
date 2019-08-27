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

	Logger log = LoggerFactory.getLogger(UploadExcelController.class);

	@Autowired
	private IDocReaderService docReaderSvc;

	@Autowired
	private IRegistroService rService;

	@GetMapping("/upload")
	@Secured({ "ROLE_ADMIN" })
	public String uploadExcel(Model model, RedirectAttributes flash, Locale locale) {
		try {
			FileWrapper r = new FileWrapper();
			model.addAttribute("fileWrapper", r);
			return "upload";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.lectura.archivo", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/doc/upload";
		}
	}

	@PostMapping("/upload")
	@Secured({ "ROLE_ADMIN" })
	public String uploadExcel(@Valid FileWrapper r, BindingResult resultValid,
			@RequestParam("admin") String nombreAutor, Model model, RedirectAttributes flash, Locale locale) {
		try {
			if (resultValid.hasErrors()) {
				return "upload";
			}

			Registro rSearched = null;
			rSearched = rService.findRegistroByVersion(r.getVersion());
			// si la versión ya existe vuelve hacia atrás.
			if (rSearched != null) {
				flash.addFlashAttribute("error",
						msgSource.getMessage("text.registro.error.version.duplicada", null, locale));
				return "redirect:/doc/upload";
			}
			docReaderSvc.readDocument(r.getFile(), r.getVersion(), locale, nombreAutor);
			flash.addFlashAttribute("success", msgSource.getMessage("text.success.msg", null, locale));
			return "redirect:/registro/listar";
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.lectura.archivo", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/doc/upload";
		}

	}
}
