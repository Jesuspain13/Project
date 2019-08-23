package es.indra.censo.controllers.auth;

import java.util.Base64;
import java.util.Base64.Decoder;

import es.indra.censo.model.Rol;
import es.indra.censo.model.Usuario;

public class UsuarioDTO {

	private String username;

	private Integer rol;

	private String passwordDecoded;
	
	private String passwordEncoded;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRol() {
		return rol;
	}

	public void setRol(Integer rol) {
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
		this.passwordDecoded = passDecoded;
	}
	
	public String getPasswordDecoded() {
		if (passwordDecoded == null || passwordDecoded.isEmpty()) {
			this.decodePassword();
		}
		return passwordDecoded;
	}

	
}
