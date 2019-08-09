package es.indra.censo.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "puesto")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Puesto implements Serializable {

	// Definición de los atributos de la tabla Puesto.

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id_puesto_auto")
	private Integer idPuestoAuto;
	
	@NaturalId
	@Column(name="id_puesto")
	private String idPuesto;

// Relación de los puestos con la planta, pendiente de ver la implementación por tema de recursividad.

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planta")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Planta planta;

	private boolean ocupado;
	
	@OneToOne(fetch= FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="id_empleado")
	private Empleado empleado;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_registro")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Registro registro;

	// Implementación de los Getters & Setters de la clase Puesto.

	
	
	public String getIdPuesto() {
		return idPuesto;
	}

	public Integer getIdPuestoAuto() {
		return idPuestoAuto;
	}

	public void setIdPuestoAuto(Integer idPuestoAuto) {
		this.idPuestoAuto = idPuestoAuto;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public void setIdPuesto(String idPuesto) {
		this.idPuesto = idPuesto;
	}

	public Planta getPlanta() {
		return planta;
	}

	public void setPlanta(Planta planta) {
		this.planta = planta;
	}
	

	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
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
