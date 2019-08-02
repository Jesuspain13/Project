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

@Entity
@Table(name = "ue")
public class Ue implements Serializable {

	public Ue() {
		this.empleados = new ArrayList<Empleado>();
	}
	
	
	// Definición de los atributos de la tabla UE.

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "id_ue")
	private String idUe;

	@Column(name = "nombre_ue")
	private String nombreUe;

	@Column(name = "ue_repercutible")
	private String ueRepercutible;

	@Column(name = "nombre_ue_repercutible")
	private String nombreUeRepercutible;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_registro")
	private Registro registro;

	@OneToMany(mappedBy = "ue", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

	public String getUeRepercutible() {
		return ueRepercutible;
	}

	public void setUeRepercutible(String ueRepercutible) {
		this.ueRepercutible = ueRepercutible;
	}

	public String getNombreUeRepercutible() {
		return nombreUeRepercutible;
	}

	public void setNombreUeRepercutible(String nombreUeRepercutible) {
		this.nombreUeRepercutible = nombreUeRepercutible;
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
