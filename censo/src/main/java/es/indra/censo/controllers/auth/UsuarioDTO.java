package es.indra.censo.controllers.auth;

import java.util.Base64;

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
	
	public String getPasswordDecoded() {
		byte[] decodedBytes = Base64.getDecoder().decode(this.getPasswordEncoded());
		this.passwordDecoded = new String(decodedBytes);
		return this.passwordDecoded;
	}

	
}
