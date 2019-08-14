package es.indra.censo.controllers.auth;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Controller
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  implements AuthenticationSuccessHandler {
 
    public LoginSuccessHandler() {
        super();
    }

//	@Autowired
//	private MessageSource messageSource;
//	
//	@Autowired
//	private LocaleResolver localeResolver;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		//auhentication nos permite obtener el nombre del usuario
		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		//crear el flash
		FlashMap fm = new FlashMap();
		
//		Locale locale = localeResolver.resolveLocale(request);
//		String mensaje = String.format(messageSource.
//				getMessage("text.login.mensaje.success", null, locale), authentication.getName());
//		
//		fm.put("success", mensaje);
//		//registrar flash en el manager
//		flashMapManager.saveOutputFlashMap(fm, request, response);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	

}
