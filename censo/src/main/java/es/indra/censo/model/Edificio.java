package es.indra.censo.model;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "edificios")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idEdificio")
public class Edificio implements Serializable {

	public Edificio() {
		this.plantas = new ArrayList<Planta>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_edificio")
	@NotNull
	private Integer idEdificio;

	@Column(name = "nombre_edificio")
	private String nombreEdificio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_complejo")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Complejo complejo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_registro")
	// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Registro registro;

	@OneToMany(mappedBy = "edificio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<Planta> plantas;

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

	public void addPlanta(Planta p) {
		this.plantas.add(p);
	}

	public List<Planta> getPlantas() {
		return plantas;
	}

	public void setPlantas(List<Planta> plantas) {
		this.plantas = plantas;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
