package es.indra.censo.controllers;

public class RegistroWrapper {

	private Integer idRegistro;

	private String planta;

	public RegistroWrapper() {
	}

	public RegistroWrapper(Integer idRegistro, String planta) {
		super();
		this.idRegistro = idRegistro;
		this.planta = planta;
	}

	public Integer getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

}
