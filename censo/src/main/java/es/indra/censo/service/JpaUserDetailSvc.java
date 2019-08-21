package es.indra.censo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.indra.censo.dao.IUsuarioDao;
import es.indra.censo.model.Rol;
import es.indra.censo.model.Usuario;

@Service
public class JpaUserDetailSvc implements UserDetailsService {

	@Autowired
	private IUsuarioDao uDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = uDao.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Username " + username + " no tiene roles asignado");
		}

		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

		for (Rol userAuth : user.getRoles()) {
			auths.add(new SimpleGrantedAuthority(userAuth.getAuthority()));
		}
		return new User(username, user.getPassword(), user.getEnabled(), true, true, true, auths);
	}

}
