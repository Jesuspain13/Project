package es.indra.censo.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LocaleController {
	
	Logger log = LoggerFactory.getLogger(LocaleController.class);
	
	@Autowired
	private MessageSource msgSource;

	@GetMapping("/locale")
	public String locale(HttpServletRequest request, Locale locale, RedirectAttributes flash) {
		try {
			String ultimaUrl = request.getHeader("referer");
	
			return "redirect:".concat(ultimaUrl);
		} catch(Exception ex) {
			log.error(ex.getMessage());
			String msg = msgSource.getMessage("text.error.generico", null, locale);
			flash.addFlashAttribute("error", String.format(msg, ex.getMessage()));
			return "redirect:/";
		}
	}
}
