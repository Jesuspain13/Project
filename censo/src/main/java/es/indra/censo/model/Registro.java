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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import es.indra.censo.model.errores.excel.Fila;

@Entity
@Table(name = "registros")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idRegistro")

public class Registro implements Serializable {

	public Registro() {
		this.complejos = new ArrayList<Complejo>();
		this.ues = new ArrayList<Ue>();
		this.uesRep = new ArrayList<UeRepercutible>();
		this.plantas = new ArrayList<Planta>();
		this.filasErroneas = new ArrayList<Fila>();
	}

	public Registro(String version) {
		this.complejos = new ArrayList<Complejo>();
		this.ues = new ArrayList<Ue>();
		this.uesRep = new ArrayList<UeRepercutible>();
		this.plantas = new ArrayList<Planta>();
		this.version = version;
		this.filasErroneas = new ArrayList<Fila>();
	}

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_registro")
	private Integer idRegistro;

	@NotEmpty
	@NotNull
	private String version;

	@Column(name = "fecha_subida")
	private Date fechaSubida;

	// Relaciones (facilitar guardado)
	@OneToMany(mappedBy = "registro", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private List<Complejo> complejos;
	
	@OneToMany(mappedBy = "registro", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private List<Planta> plantas;

	@OneToMany(mappedBy = "registro", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private List<Ue> ues;
	
	@OneToMany(mappedBy = "registro", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private List<UeRepercutible> uesRep;
	
	@OneToMany(mappedBy="registro", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<Fila> filasErroneas;

	public List<UeRepercutible> getUesRep() {
		return uesRep;
	}

	public void setUesRep(List<UeRepercutible> uesRep) {
		this.uesRep = uesRep;
	}

	@ManyToOne(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="usuario_id")
	private Usuario autorSubida;

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

	public List<Planta> getPlantas() {
		return plantas;
	}

	public void setPlantas(List<Planta> plantas) {
		this.plantas = plantas;
	}
	
	public void clearPlantas() {
		this.plantas.clear();
	}
	
	public void addPlanta(Planta p) {
		this.plantas.add(p);
	}

	public List<Ue> getUes() {
		return ues;
	}

	public void setUes(List<Ue> ues) {
		this.ues = ues;
	}

	public void addUes(Ue ue) {
		this.ues.add(ue);
	}
	

	public Usuario getAutorSubida() {
		return autorSubida;
	}

	public void setAutorSubida(Usuario autorSubida) {
		this.autorSubida = autorSubida;
	}
	
	public void addUeRepercutible(UeRepercutible ueRep) {
		this.uesRep.add(ueRep);
	}
	


	public List<Fila> getFilasErroneas() {
		return filasErroneas;
	}

	public void setFilasErroneas(List<Fila> filasErroneas) {
		this.filasErroneas = filasErroneas;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
