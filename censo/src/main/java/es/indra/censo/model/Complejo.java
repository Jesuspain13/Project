package es.indra.censo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="complejos")
public class Complejo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Id
	@Column(name="id_complejo")
	private String idComplejo;
	
	@Column(name="nombre_complejo")
	private String nombreComplejo;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdComplejo() {
		return idComplejo;
	}

	public void setIdComplejo(String idComplejo) {
		this.idComplejo = idComplejo;
	}

	public String getNombreComplejo() {
		return nombreComplejo;
	}

	public void setNombreComplejo(String nombreComplejo) {
		this.nombreComplejo = nombreComplejo;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
