package es.indra.censo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "puesto")
public class Puesto implements Serializable {

	// Definición de los atributos de la tabla Puesto.

	@Id
	@Column(name = "id_puesto")
	private Integer idPuesto;

// Relación de los puestos con la planta, pendiente de ver la implementación por tema de recursividad.

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planta")
	private Planta planta;

	private boolean ocupado;
	
	@OneToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="id_empleado")
	private Empleado empleado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_registro")
	private Registro registro;

	// Implementación de los Getters & Setters de la clase Puesto.

	public Integer getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(Integer idPuesto) {
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
