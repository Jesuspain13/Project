package es.indra.censo.controllers;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class NuevoEmpleadoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty
	public String nombre;
	
	@NotEmpty
	public String apellidos;
	
	public String nick;
	
	public int numero;
	
	public int puesto;
	
	public int registro;
	
	public int ue;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getPuesto() {
		return puesto;
	}

	public void setPuesto(int puesto) {
		this.puesto = puesto;
	}

	public int getRegistro() {
		return registro;
	}

	public void setRegistro(int registro) {
		this.registro = registro;
	}

	public int getUe() {
		return ue;
	}

	public void setUe(int ue) {
		this.ue = ue;
	}
	
	

}
