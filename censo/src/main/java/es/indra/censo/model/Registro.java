package es.indra.censo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="registros")
public class Registro implements Serializable {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_registro")
	private Integer idRegistro;
	
	private String version;
	
	@Column(name="fecha_subida")
	private Date fechaSubida;
	
	//private Integer idAdmin;

	public Integer getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getFechaSubida() {
		return fechaSubida;
	}

	public void setFechaSubida(Date fechaSubida) {
		this.fechaSubida = fechaSubida;
	}
	
	@PrePersist
	public void establecerFechaSubida() {
		this.fechaSubida = new Date();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
