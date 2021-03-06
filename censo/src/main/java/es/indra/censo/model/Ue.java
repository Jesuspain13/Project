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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "ue")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = {"id", "ueRepercutible"})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Ue implements Serializable {

	public Ue() {
		this.empleados = new ArrayList<Empleado>();
	}

	// Definición de los atributos de la tabla UE.

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "id_ue")
	private String idUe;

	@Column(name = "nombre_ue")
	private String nombreUe;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_ue_repercutible")
	private UeRepercutible ueRepercutible;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_registro")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private Registro registro;

	@OneToMany(mappedBy = "ue", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private List<Empleado> empleados;

	// Implementación de los Getters & Setters de la clase UE.

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdUe() {
		return idUe;
	}

	public void setIdUe(String idUe) {
		this.idUe = idUe;
	}

	public String getNombreUe() {
		return nombreUe;
	}

	public void setNombreUe(String nombreUe) {
		this.nombreUe = nombreUe;
	}

	public UeRepercutible getUeRepercutible() {
		return ueRepercutible;
	}

	public void setUeRepercutible(UeRepercutible ueRepercutible) {
		this.ueRepercutible = ueRepercutible;
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}

	public Registro getRegistro() {
		return registro;
	}

	public void addEmpleado(Empleado e) {
		this.empleados.add(e);
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
