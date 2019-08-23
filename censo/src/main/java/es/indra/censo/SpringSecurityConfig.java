package es.indra.censo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.indra.censo.controllers.auth.LoginSuccessHandler;
import es.indra.censo.service.JpaUserDetailSvc;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JpaUserDetailSvc userDetailsService;

	@Autowired
	private DaoAuthenticationProvider authProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/home*", "/css/**", "/js/**", "/font/**", "/img/**", "/scs**", "/locale").permitAll()
				
				.antMatchers("/doc/upload").hasAnyRole("ADMIN").anyRequest().authenticated().and().formLogin()
				.successHandler(loginSuccessHandler).loginPage("/login").permitAll().and().logout().permitAll().and()
				.exceptionHandling().accessDeniedPage("/error_403");
	}

	@Autowired
	public void ConfigurerGlobal(AuthenticationManagerBuilder build) {
		// UserBuilder ub = User.withDefaultPasswordEncoder();

		try {
			build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

}
