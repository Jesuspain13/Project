package es.indra.censo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="registros")
public class Registro implements Serializable {

	public Registro() {
		this.complejos = new ArrayList<Complejo>();
	}
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_registro")
	private Integer idRegistro;
	
	private String version;
	
	@Column(name="fecha_subida")
	private Date fechaSubida;
	
	// Relaciones (facilitar guardado)
	@OneToMany(mappedBy="registro", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Complejo> complejos;
	
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
	
	public List<Complejo> getComplejos() {
		return complejos;
	}

	public void setComplejos(List<Complejo> complejos) {
		this.complejos = complejos;
	}

	public void addComplejo(Complejo c) {
		this.complejos.add(c);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
