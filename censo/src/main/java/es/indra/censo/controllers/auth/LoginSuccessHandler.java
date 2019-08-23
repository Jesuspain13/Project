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
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	public LoginSuccessHandler() {
		super();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// auhentication nos permite obtener el nombre del usuario
		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		// crear el flash
		FlashMap fm = new FlashMap();


		super.onAuthenticationSuccess(request, response, authentication);
	}

}
