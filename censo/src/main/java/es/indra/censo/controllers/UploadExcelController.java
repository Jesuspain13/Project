package es.indra.censo.controllers;

import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.indra.censo.controllers.paginator.PageRender;
import es.indra.censo.docreader.FileWrapper;
import es.indra.censo.model.Registro;
import es.indra.censo.model.errores.excel.Fila;
import es.indra.censo.service.IDocReaderService;
import es.indra.censo.service.IRegistroService;
import es.indra.censo.service.errorexcel.IFilasExcelSvc;

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
	
	@Autowired
	private IFilasExcelSvc erroresSvc;

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
			Map<String, Object> idRegistroYPaginador = docReaderSvc.readDocument(r.getFile(), r.getVersion(), locale, nombreAutor);
			Page<Fila> page = (Page<Fila>) idRegistroYPaginador.get("page");
			PageRender<Fila> pgr = new PageRender<Fila>("/doc/errores/pagina", page);
			Integer idRegistro = (int) idRegistroYPaginador.get("idRegistro");
			model.addAttribute("pageSelected", page);
			model.addAttribute("page", pgr);
			model.addAttribute("idRegistro", idRegistro);
			model.addAttribute("success", msgSource.getMessage("text.success.msg", null, locale));
			return "datoserroneos";
			
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.lectura.archivo", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/doc/upload";
		}

	}
	
	@GetMapping("/errores/pagina")
	@Secured({ "ROLE_ADMIN" })
	public String buscarPaginaErrores(@RequestParam(name="page") int page,
			@RequestParam(name="registro") int idRegistro,
			Model model, RedirectAttributes flash, Locale locale) {
		try {
			Page<Fila> pageSelected = erroresSvc.encontrarErroresDelRegistro(idRegistro, page);
			PageRender<Fila> pgr = new PageRender<Fila>("/doc/errores/pagina", pageSelected);
			model.addAttribute("pageSelected", pageSelected);
			model.addAttribute("page", pgr);
			model.addAttribute("idRegistro", idRegistro);
			
			return "datoserroneos";
			 
		} catch (Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}
	
//	@GetMapping("/errores/pagina")
//	@Secured({ "ROLE_ADMIN" })
//	public String buscarOtraPaginaErrores(@RequestParam(name="page") int page,
//			@RequestParam(name="registro") int idRegistro,
//			Model model, RedirectAttributes flash, Locale locale) {
//		try {
//			
//			return "redirect:/registro/listar";
//			 
//		} catch (Exception ex) {
//			log.error(ex.getMessage());
//			String msg = msgSource.getMessage("text.error.generico", null, locale);
//			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
//			return "redirect:/";
//		}
//	}
}
