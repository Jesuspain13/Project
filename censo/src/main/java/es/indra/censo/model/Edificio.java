package es.indra.censo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "edificios")
public class Edificio implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_edificio")
	private Integer idEdificio;

	@Column(name = "nombre_edificio")
	private String nombreEdificio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_complejo")
	private Complejo complejo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_registro")
	private Registro registro;

	public Integer getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Integer idEdificio) {
		this.idEdificio = idEdificio;
	}

	public String getNombreEdificio() {
		return nombreEdificio;
	}

	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
	}

	public Complejo getComplejo() {
		return complejo;
	}

	public void setComplejo(Complejo complejo) {
		this.complejo = complejo;
	}

	public Registro getRegistro() {
		return registro;
	}
	
	

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
