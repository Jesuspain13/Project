package es.indra.censo.controllers.auth;

import java.util.Base64;
import java.util.Base64.Decoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.indra.censo.model.Rol;
import es.indra.censo.model.Usuario;

public class UsuarioDTO {
	
	private Integer idUser;

	private String username;

	private String rol;

	private String passwordDecoded;
	
	private String passwordEncoded;
	
	

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getPasswordEncoded() {
		return passwordEncoded;
	}

	public void setPasswordEncoded(String password) {
		this.passwordEncoded = password;
	}
	
	public void decodePassword() {
		Decoder decoder = Base64.getDecoder();
		String pass = this.getPasswordEncoded();
		byte[] decodedBytes = decoder.decode(pass);
		String passDecoded= new String(decodedBytes);
		System.out.println(passDecoded);
		this.setPasswordDecoded(passDecoded);
	}
	
	public String decodePasswordEncoded() {
		if (passwordDecoded == null || passwordDecoded.isEmpty()) {
			this.decodePassword();
		}
		return getPasswordDecoded();
	}

	public String getPasswordDecoded() {
		return passwordDecoded;
	}

	public void setPasswordDecoded(String passwordDecoded) {
		this.passwordDecoded = passwordDecoded;
	}
	
	
	
	

	
}
